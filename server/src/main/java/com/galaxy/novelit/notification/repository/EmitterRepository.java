package com.galaxy.novelit.notification.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Repository
public class EmitterRepository {
    // <UUID, SseEmitter>
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String id, SseEmitter sseEmitter) {
        emitters.put(id, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String id, Object object) {
        eventCache.put(id, object);
    }

    public SseEmitter get (String id) {
        for (Entry<String, SseEmitter> entry : emitters.entrySet()) {
            if (entry.getKey().equals(id)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, SseEmitter> findAllEmittersStartWithId(String id) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(id))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithId(String id) {
        return eventCache.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(id))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    /*public void deleteAllStartByWithId(String id) {
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(id)) emitters.remove(key);
        });
    }*/

}
