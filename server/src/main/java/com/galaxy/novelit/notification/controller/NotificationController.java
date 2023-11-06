package com.galaxy.novelit.notification.controller;

import com.galaxy.novelit.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequestMapping("/notifications")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    // 처음 구독
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@RequestParam("subscriberUUID") String subscriberUUID)
    {
        return ResponseEntity.ok(notificationService.subscribe(subscriberUUID));
    }

    // 코멘트 알람 테스트
    /*@PostMapping("/send")
    public ResponseEntity<Void> alertComment(@RequestBody NotificationRequestDto notificationRequestDto) {
        notificationService.alertComment(notificationRequestDto);
        return ResponseEntity.ok().build();
    }*/
}
