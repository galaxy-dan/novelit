package com.galaxy.novelit.alarm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseRepository {

    void put(String key, SseEmitter sseEmitter);

    Optional<SseEmitter> get(String key);

    List<SseEmitter> getListByKeyPrefix(String keyPrefix);

    List<String> getKeyListByKeyPrefix(String keyPrefix);

    void remove(String key);
}
