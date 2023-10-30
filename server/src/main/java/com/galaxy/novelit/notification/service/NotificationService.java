package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.notification.dto.Notification;
import com.galaxy.novelit.notification.repository.EmitterRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(Long userId)
    {
        SseEmitter emitter = createEmitter(userId);

        sendToClient(userId, "이벤트 스트림 생성 [유저ID = " + userId + "]");
        return emitter;
    }

    public void notify(Long userId, Object event)
    {
        sendToClient(userId, event);
    }

    private void sendToClient(Long id, Object data)
    {
        SseEmitter emitter = emitterRepository.get(id);
        if (emitter != null)
        {
            try{
                emitter.send(SseEmitter.event()
                    .id(String.valueOf(id))
                    .name("SSE")
                    .data(data));
            } catch (IOException exception)
            {
                emitterRepository.deleteById(id);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(Long id)
    {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }

    /*public void send(String userUuid){
        Notification notification = createNotification(Member receiver)
    }*/
}
