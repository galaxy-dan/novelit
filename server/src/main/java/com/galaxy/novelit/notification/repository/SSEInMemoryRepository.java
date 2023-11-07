package com.galaxy.novelit.notification.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SSEInMemoryRepository implements SSERepository{

    private final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();


    @Override
    public void put(String key, SseEmitter sseEmitter) {

    }

    @Override
    public Optional<SseEmitter> get(String key) {
        return Optional.empty();
    }
}
