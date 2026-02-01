package com.galaxy.novelit.notification.repository;

import com.galaxy.novelit.notification.response.NotificationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository{
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, NotificationInfo> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String id, SseEmitter sseEmitter) {
        emitters.put(id, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String id, NotificationInfo notificationInfo) {
        eventCache.put(id, notificationInfo);
    }

    @Override
    public SseEmitter getEmitter(String id) {
        return emitters.get(id);
    }

    @Override
    public Map<String, SseEmitter> findAllEmittersStartWithId(String id) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(id))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, NotificationInfo> findAllEventCacheStartWithId(String id) {
        return eventCache.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(id))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllStartWithById(String id) {
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(id)) emitters.remove(key);
        });
    }

    @Override
    public void deleteAllEventCacheStartWithById(String id) {
        eventCache.forEach((key, notificationInfo) ->{
            if (key.startsWith(id)) eventCache.remove(key);
        });
    }
}
