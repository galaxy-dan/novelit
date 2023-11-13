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
import jakarta.servlet.http.HttpServletResponse;
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
    private static final Long DEFAULT_TIMEOUT = 10L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final AlarmRedisService alarmRedisService;

    public SseEmitter subscribe(String subscriberUUID, String lastEventId, HttpServletResponse response)
    {
        String id = subscriberUUID + "_" + System.currentTimeMillis();



        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        //nginx리버스 프록시에서 버퍼링 기능으로 인한 오동작 방지
        response.setHeader("X-Accel-Buffering", "no");

        emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onError((e) -> emitterRepository.deleteAllStartByWithId(id));

        sendSubscribe(emitter, id, "Connection");

        if (!lastEventId.isEmpty()) {
            Map<String, SseEmitter> events = emitterRepository.findAllStartById(subscriberUUID);
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
        String directoryName = directory.getName();
        NotificationResponseDto notificationResponseDto = NotificationResponseDto.createAlarmComment(
            commentNickname, id);

        Map<String,SseEmitter> sseEmitters = emitterRepository.findAllStartById(id);

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
                    emitterRepository.deleteAllStartByWithId(id);
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
            emitterRepository.deleteAllStartByWithId(id);
            emitter.completeWithError(exception);
        }
    }


/*    // 처음 구독
    private SseEmitter createEmitter(String id)
    {


        return emitter;
    }*/

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
