package com.galaxy.novelit.alarm.service;

import com.galaxy.novelit.alarm.dto.SseEventName;
import com.galaxy.novelit.alarm.repository.SseRepository;
import com.galaxy.novelit.alarm.repository.SseRepositoryKeyRule;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.notification.dto.response.SseConnection;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmService {
    @Value("${sse.timeout}")
    private String sseTimeout;
    private static final String UNDER_SCORE = "_";
    private static final String CONNECTED = "alertComment";
    //private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final SseRepository sseRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public SseEmitter subscribe(String username, String lastEventId, LocalDateTime now) {
        SseEmitter sse = new SseEmitter(Long.parseLong(sseTimeout));
        String key = new SseRepositoryKeyRule(username, SseEventName.ALARM_LIST,
            now).toCompleteKeyWhichSpecifyOnlyOneValue();

        sse.onCompletion(() -> {
            log.info("onCompletion callback");
            sseRepository.remove(key);
        });
        sse.onTimeout(() -> {
            log.info("onTimeout callback");
            //만료 시 Repository에서 삭제 되어야함.
            sse.complete();
        });

        sseRepository.put(key, sse);
        try {
            sse.send(SseEmitter.event()
                .name(CONNECTED)
                .id(getEventId(username, now, SseEventName.ALARM_LIST))
                .data(SseConnection.builder()
                    .type("connection")
                    .content("최초 연결")
                    .build()));
        } catch (IOException exception) {
            sseRepository.remove(key);
            log.info("SSE Exception: {}", exception.getMessage());
            throw new RuntimeException();
        }

        // 중간에 연결이 끊겨서 다시 연결할 때, lastEventId를 통해 기존의 받지못한 이벤트를 받을 수 있도록 할 수 있음.
        // 한번의 알림이나 새로고침을 받으면 알림을 slice로 가져오기 때문에
        // 수신 못한 응답을 다시 보내는 로직을 구현하지 않음.
        return sse;
    }

    /**
     *  특정 유저의 특정 sse 이벤트에 대한 id를 생성한다.
     *  위 조건으로 여러개 정의 될 경우 now 로 구분한다.
     * @param userId
     * @param now
     * @param eventName
     * @return*/

    private String getEventId(String userId, LocalDateTime now, SseEventName eventName) {
        return userId + UNDER_SCORE + eventName.getValue() + UNDER_SCORE + now;
    }

    /**
     * redis pub시 userId와 sseEventName을 합쳐서 보낸다.
     * @param userId
     * @param sseEventName
     * @return*/
    @Transactional
    public void send(String alarmReceiverId, SseEventName sseEventName) {
        redisTemplate.convertAndSend(sseEventName.getValue(),
            getRedisPubMessage(alarmReceiverId, sseEventName));
    }

    private String getRedisPubMessage(String userId, SseEventName sseEventName) {
        return userId + UNDER_SCORE + sseEventName.getValue();
    }

}