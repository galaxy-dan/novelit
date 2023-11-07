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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final AlarmRedisService alarmRedisService;

    public SseEmitter subscribe(String subscriberUUID)
    {
        SseEmitter emitter = createEmitter(subscriberUUID);

        sendSubscribe(subscriberUUID, "이벤트 스트림 생성 [유저ID = " + subscriberUUID + "]");
        return emitter;
    }


    @Override
    public void alertComment(String commentNickname, String directoryUUID) {
        sseAlertComment(commentNickname, directoryUUID);
    }

    private void sseAlertComment(String commentNickname, String directoryUUID) {
        // directoryUUID == workspaceUUID
        Directory directory = directoryRepository.findDirectoryByUuid(
                directoryUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("작품이 없습니다!"));

        String subscriberUUID = directory.getUserUUID();

        SseEmitter emitter = emitterRepository.get(subscriberUUID);

        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(
            commentNickname, subscriberUUID);

        if (emitter != null) {
            try {
                // 알람UUID, 함수, 텍스트 보내주기
                emitter.send(SseEmitter.event()
                    .id(notificationResponseDto.getNotificationUUID()) // publisher
                    .name("alertComment")
                    .data(notificationResponseDto.getNotificationContent(), MediaType.TEXT_PLAIN));

                    alarmRedisService.save(AlarmRedisRequestDto.builder()
                        .subUUID(subscriberUUID)
                        .notiUUID(notificationResponseDto.getNotificationUUID())
                        .build());


            } catch (IOException e) {
                // exception되면 알람UUID 삭제
                emitterRepository.deleteById(notificationResponseDto.getNotificationUUID());
                emitter.completeWithError(e);
            }
        }
    }

    /*private void sseAlertComment(NotificationRequestDto notificationRequestDto) {
        SseEmitter emitter = emitterRepository.get(notificationRequestDto.getSubscriberUUID());

        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(
            notificationRequestDto);

        //log.info(notificationResponseDto.getNotificationContent());

        if (emitter != null) {
            try {
                // 알람UUID, 함수, 텍스트 보내주기
                emitter.send(SseEmitter.event()
                    .id(notificationResponseDto.getNotificationUUID())
                    .name("alertComment")
                    .data(notificationResponseDto.getNotificationContent(), MediaType.TEXT_PLAIN));
            } catch (IOException e) {
                // exception되면 알람UUID 삭제
                emitterRepository.deleteById(notificationResponseDto.getNotificationUUID());
                emitter.completeWithError(e);
            }
        }
    }*/

    // 처음 구독
    private void sendSubscribe(String subscriberUUID, Object data)
    {
        SseEmitter emitter = emitterRepository.get(subscriberUUID);
        if (emitter != null)
        {
            try{
                emitter.send(SseEmitter.event()
                    .id(subscriberUUID)
                    .name("alertComment") // alertComment stream 생성
                    .data(data));
            } catch (IOException exception)
            {
                emitterRepository.deleteById(subscriberUUID);
                emitter.completeWithError(exception);
            }
        }
    }

    // 처음 구독
    private SseEmitter createEmitter(String subscriberUUID)
    {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(subscriberUUID, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(subscriberUUID));
        emitter.onTimeout(() -> emitterRepository.deleteById(subscriberUUID));

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
