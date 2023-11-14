package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.dto.response.NotificationResponseDto;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.SseConnection;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.repository.EmitterRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private static final Long DEFAULT_TIMEOUT = 120L * 60 * 1000; // 2시간 지속

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final AlarmRedisService alarmRedisService;

    public SseEmitter subscribe(String lastEventId, String subscriberUUID, HttpServletResponse response)
    {
        String id = subscriberUUID + "_" + System.currentTimeMillis();

        // subscriberUUID
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        //nginx리버스 프록시에서 버퍼링 기능으로 인한 오동작 방지
        response.setHeader("X-Accel-Buffering", "no");

        emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onError((e) -> emitterRepository.deleteAllStartByWithId(id));

        sendToClient(emitter, id, SseConnection.builder()
            .type("Connection")
            .content("최초연결")
            .build());

        if (!lastEventId.isEmpty()) {
            Map<String, SseEmitter> events = emitterRepository.findAllStartById(subscriberUUID);
            events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    // 처음 구독
    private void sendToClient(SseEmitter emitter, String id, Object data)
    {
        try{
            emitter.send(SseEmitter.event()
                .id(id)
                .name("alertComment")
                .data(data));
        } catch (IOException exception)
        {
            emitterRepository.deleteAllStartByWithId(id);
            emitter.completeWithError(exception);
        }
    }

    @Override
    @Transactional
    // 알림 보낼 로직에 send 메서드 호출하면 됨
    public void send(String commentNickname, String directoryUUID, String publisherUUID) {
        // 파일 찾기
        Directory directory = directoryRepository.findDirectoryByUuid(
                directoryUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("작품이 없습니다!"));

        // 유저UUID, 파일 이름찾기
        String id = directory.getUserUUID();
        String directoryName = directory.getName();

        // 알림 responseDto 만들기
        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(
            commentNickname, id);

        // subscriberUUID로 시작하는 emitter 찾기
        Map<String,SseEmitter> sseEmitters = emitterRepository.findAllStartById(id);

        sseEmitters.forEach(
            (key, emitter) -> {
                // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                emitterRepository.saveEventCache(key, notificationResponseDto);

                sendToClient(emitter, key, notificationResponseDto);

                // 알림 레디스에 저장
                alarmRedisService.save(AlarmRedisRequestDto.builder()
                    .pubUUID(publisherUUID)
                    .pubName(commentNickname)
                    .subUUID(id)
                    .directoryName(directoryName)
                    .build());
            }
        );
    }

    //redis pub시 pub UUID와 notiResDto을 합쳐서 보낸다.
    // @param String pubUUID
    // @Body NotiResDto notiResDto
    private String getRedisPubMessage(String pubUUID, NotificationResponseDto notificationResponseDto) {
        return pubUUID + "->" + notificationResponseDto.getSubscriberUUID();
    }

    private User getUserByUserUUIDOrException(String userUUID) {
        User user = userRepository.findByUserUUID(userUUID);
        return user;
    }
}
