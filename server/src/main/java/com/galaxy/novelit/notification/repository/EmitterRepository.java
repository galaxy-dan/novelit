package com.galaxy.novelit.notification.repository;

import com.galaxy.novelit.notification.response.NotificationInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository{
    SseEmitter save(String id, SseEmitter sseEmitter);

    void saveEventCache(String id, NotificationInfo notificationInfo);

    SseEmitter getEmitter(String id);

    Map<String, SseEmitter> findAllEmittersStartWithId(String id);

    Map<String, NotificationInfo> findAllEventCacheStartWithId(String id);

    void deleteById(String id);

    void deleteAllStartWithById(String id);

    void deleteAllEventCacheStartWithById(String id);
}
