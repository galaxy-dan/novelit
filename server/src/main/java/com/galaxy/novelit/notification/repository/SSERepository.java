package com.galaxy.novelit.notification.repository;

import java.util.Optional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SSERepository {

    void put(String key, SseEmitter sseEmitter);

    Optional<SseEmitter> get(String key);
}
