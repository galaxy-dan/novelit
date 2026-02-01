package com.galaxy.novelit.notification.controller;

import com.galaxy.novelit.notification.redis.response.AlarmGetResponse;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AlarmRedisService alarmRedisService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Authentication authentication,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
        HttpServletResponse response) {

        String subUUID = authentication.getName();

        response.setHeader("X-Accel-Buffering", "no");

        SseEmitter sseEmitter = notificationService.subscribe(lastEventId, subUUID, response);

        return ResponseEntity.ok(sseEmitter);
    }

    @GetMapping("/alarmlist")
    public ResponseEntity<List<AlarmGetResponse>> getAllAlarmlist(Authentication authentication) {
        String subUUID = authentication.getName();
        return ResponseEntity.ok(alarmRedisService.getAllList(subUUID));
    }
}
