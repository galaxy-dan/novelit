package com.galaxy.novelit.notification.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(String lastEventId, String subscriberUUID, HttpServletResponse response);
    void send(String commentNickname, String directoryUUID, String publisherUUID);
}
