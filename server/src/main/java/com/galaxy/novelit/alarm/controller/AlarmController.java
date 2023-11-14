package com.galaxy.novelit.alarm.controller;

import com.galaxy.novelit.alarm.service.AlarmService;
import com.galaxy.novelit.common.utils.CustomTimeUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    //@PreAuthorize("hasAuthority('alarm.read')")
    //@Operation(summary = "알람 sse 구독", description = "알람 sse 구독, sseEmitter객체 반환.")
    @GetMapping(value = "/alarm/subscribe", produces = "text/event-stream")
    public SseEmitter alarmSubscribe(
        //@ApiParam(hidden = true) @AuthenticationPrincipal UserDetails user,
        Authentication authentication,
        @RequestHeader(value = "Last-Event-ID", required = false) String lastEventId,
        HttpServletResponse response) {
        //nginx리버스 프록시에서 버퍼링 기능으로 인한 오동작 방지
        response.setHeader("X-Accel-Buffering", "no");
        LocalDateTime now = CustomTimeUtils.nowWithoutNano();
        return alarmService.subscribe(authentication.getName(), lastEventId, now);
    }
}