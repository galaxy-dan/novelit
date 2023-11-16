package com.galaxy.novelit.notification.controller;

import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
import com.galaxy.novelit.notification.redis.service.AlarmRedisService;
import com.galaxy.novelit.notification.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
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

@Slf4j
@RequestMapping("/notifications")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final AlarmRedisService alarmRedisService;

    // 처음 구독
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Authentication authentication,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
        HttpServletResponse response) {
        String subUUID = authentication.getName();
        response.setHeader("X-Accel-Buffering", "no");
        return ResponseEntity.ok(notificationService.subscribe(lastEventId, subUUID, response));
    }

    @GetMapping("/alarmlist")
    public ResponseEntity<List<AlarmGetResponseDto>> getAllAlarmlist(Authentication authentication) {
        String subUUID = authentication.getName();
        log.info("getAllAlarmlist: {}" ,subUUID);
        return ResponseEntity.ok(alarmRedisService.getAllList(subUUID));
    }

    /*@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@RequestParam("subscriberUUID") String subUUID,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
        HttpServletResponse response)
    {
        return ResponseEntity.ok(notificationService.subscribe(lastEventId, subUUID, response));
    }

    @GetMapping("/alarmlist")
    public ResponseEntity<List<AlarmGetResponseDto>> getAllAlarmlist(@RequestParam("subscriberUUID") String subUUID) {
        return ResponseEntity.ok(alarmRedisService.getAllList(subUUID));
    }*/

    // 코멘트 알람 테스트
    /*@PostMapping("/send")
    public ResponseEntity<Void> alertComment(Authentication authentication) {
        notificationService.alertComment(authentication.getName());
        return ResponseEntity.ok().build();
    }*/
}
