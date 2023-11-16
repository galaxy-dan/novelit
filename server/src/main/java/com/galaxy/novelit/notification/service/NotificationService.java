package com.galaxy.novelit.notification.service;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(String lastEventId, String subscriberUUID, HttpServletResponse response);

    void notify(String commentNickname, String directoryUUID, String publisherUUID);

    void notice(CommentAddRequestDto commentAddRequestDto);
}
