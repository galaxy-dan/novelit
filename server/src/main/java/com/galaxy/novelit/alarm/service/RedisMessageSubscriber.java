package com.galaxy.novelit.alarm.service;

import com.galaxy.novelit.alarm.dto.SseEventName;
import com.galaxy.novelit.alarm.repository.SseRepository;
import com.galaxy.novelit.alarm.repository.SseRepositoryKeyRule;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisMessageSubscriber implements MessageListener {

    private static final String UNDER_SCORE = "_";
    private final SseRepository sseRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Redis Pub/Sub message received: {}", message.toString());
        String[] strings = message.toString().split(UNDER_SCORE);
        String subscriberUUID = "subscriberUUID";
        //Long userId = Long.parseLong(strings[0]);
        SseEventName eventName = SseEventName.getEnumFromValue(strings[1]);
        String keyPrefix = new SseRepositoryKeyRule(subscriberUUID, eventName,
            null).toCompleteKeyWhichSpecifyOnlyOneValue();

        //LocalDateTime now = CustomTimeUtils.nowWithoutNano();
        LocalDateTime now = LocalDateTime.now();

        sseRepository.getKeyListByKeyPrefix(keyPrefix).forEach(key -> {
            SseEmitter emitter = sseRepository.get(key).get();
            try {
                emitter.send(SseEmitter.event()
                    .id(getEventId(subscriberUUID, now, eventName))
                    .name(eventName.getValue())
                    .data(eventName.getValue()));
            } catch (IOException e) {
                sseRepository.remove(key);
                log.error("SSE send error", e);
                throw new RuntimeException();
                //throw new SseException(ErrorCode.SSE_SEND_ERROR);
            }
        });
    }

    /**
     *  특정 유저의 특정 sse 이벤트에 대한 id를 생성한다.
     *  위 조건으로 여러개 정의 될 경우 now 로 구분한다.
     * @param userId
     * @param now
     * @param eventName
     * @return
     */
    private String getEventId(String userId, LocalDateTime now, SseEventName eventName) {
        return userId + UNDER_SCORE + eventName.getValue() + UNDER_SCORE + now;
    }
}