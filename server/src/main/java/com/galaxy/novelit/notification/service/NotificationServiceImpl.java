package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.dto.response.NotificationResponseDto;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.repository.EmitterRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private static final Long DEFAULT_TIMEOUT = 5L;

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final AlarmRedisService alarmRedisService;

    public SseEmitter subscribe(String subscriberUUID, String lastEventId)
    {
        String id = subscriberUUID + "_" + System.currentTimeMillis();

        SseEmitter emitter = createEmitter(id);

        sendSubscribe(emitter, id, "Connection");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(subscriberUUID);
            events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendSubscribe(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }


    @Override
    public void alertComment(String commentNickname, String directoryUUID, String publisherUUID) {
        sseAlertComment(commentNickname, directoryUUID, publisherUUID);
    }

    private void sseAlertComment(String commentNickname, String directoryUUID, String publisherUUID) {
        // directoryUUID == workspaceUUID
        Directory directory = directoryRepository.findDirectoryByUuid(
                directoryUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("작품이 없습니다!"));

        String id = directory.getUserUUID();
        //log.info("subscriberUUID : {}", subscriberUUID);
        String directoryName = directory.getName();
        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(
            commentNickname, id);

        Map<String,SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);

        sseEmitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, notificationResponseDto);

                try {
                    // 알람UUID, 함수, 텍스트 보내주기
                    emitter.send(SseEmitter.event()
                        .id(id) // publisher
                        .name("alertComment")
                        .data(notificationResponseDto.getNotificationContent(), MediaType.TEXT_PLAIN));

                    //log.info("pubName : {}, subUUID : {}, directoryName : {}", commentNickname, subscriberUUID, directoryName);

                    alarmRedisService.save(AlarmRedisRequestDto.builder()
                        .pubUUID(publisherUUID)
                        .pubName(commentNickname)
                        .subUUID(id)
                        .directoryName(directoryName)
                        .build());


                } catch (IOException e) {
                    // exception되면 알람UUID 삭제
                    emitterRepository.deleteById(id);
                    emitter.completeWithError(e);
                }
            }
        );

        //log.info(emitter.toString());

        /*if (emitter != null) {

        }*/
    }


    // 처음 구독
    private void sendSubscribe(SseEmitter emitter, String id, Object data)
    {
        try{
            emitter.send(SseEmitter.event()
                .id(id)
                .name("connection") // alertComment stream 생성
                .data(data));
        } catch (IOException exception)
        {
            emitterRepository.deleteById(id);
            emitter.completeWithError(exception);
        }
    }


    // 처음 구독
    private SseEmitter createEmitter(String id)
    {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
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
