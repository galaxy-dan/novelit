package com.galaxy.novelit.notification.repository;

import java.util.Map;
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
    private final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    public SseEmitter save(String subscriberUUID, SseEmitter emitter) {
        emitters.put(subscriberUUID, emitter);
        return emitter;
    }

    public void deleteAllStartByWithId(String id) {
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(id)) emitters.remove(key);
        });
    }

    /*public void deleteById(String subscriberUUID) {
        emitters.remove(subscriberUUID);
    }
*/
    public SseEmitter get(String subscriberUUID) {
        return emitters.get(subscriberUUID);
    }

    public void saveEventCache(String key, Object object) {
        cacheMap.put(key, object);
    }

    public Map<String, SseEmitter> findAllStartById(String id) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(id))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /*public Map<String, SseEmitter> findAllStartWithById(String subscriberUUID) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(subscriberUUID))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }*/

    public Map<String, Object> findAllEventCacheStartWithId(String subscriberUUID) {
        return cacheMap.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(subscriberUUID))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
