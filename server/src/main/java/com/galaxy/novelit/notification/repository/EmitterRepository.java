package com.galaxy.novelit.notification.repository;

import com.galaxy.novelit.notification.dto.response.NotificationResponseDto;
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
    private final Map<String, NotificationResponseDto> cacheMap = new ConcurrentHashMap<>();

    public void save(String subscriberUUID, SseEmitter emitter) {
        emitters.put(subscriberUUID, emitter);
    }

    public void deleteById(String subscriberUUID) {
        emitters.remove(subscriberUUID);
    }

    public SseEmitter get(String subscriberUUID) {
        return emitters.get(subscriberUUID);
    }

    public void saveEventCache(String key, NotificationResponseDto notificationResponseDto) {
        cacheMap.put(key, notificationResponseDto);
    }

    public Map<String, SseEmitter> findAllStartWithById(String subscriberUUID) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(subscriberUUID))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithId(String subscriberUUID) {
        return cacheMap.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(subscriberUUID))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
