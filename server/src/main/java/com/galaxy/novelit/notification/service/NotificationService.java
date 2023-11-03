package com.galaxy.novelit.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(String subscriberUUID);

    //void alertComment(NotificationRequestDto notificationRequestDto);

    void alertComment(String commentNickname, String directoryUUID);
}
