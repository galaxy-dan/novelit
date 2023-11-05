package com.galaxy.novelit.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Repository
public class EmitterRepository {

    // <UUID, SseEmitter>
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(String subscriberUUID, SseEmitter emitter) {
        emitters.put(subscriberUUID, emitter);
    }

    public void deleteById(String subscriberUUID) {
        emitters.remove(subscriberUUID);
    }

    public SseEmitter get(String subscriberUUID) {
        return emitters.get(subscriberUUID);
    }
}
