package com.galaxy.novelit.share.controller;

import com.galaxy.novelit.share.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/share")
@RequiredArgsConstructor
@RestController
public class ShareController {

    private final ShareService shareService;

    @GetMapping
    public ResponseEntity<String> shareContent(@RequestParam("directoryUUID") String directoryUUID) {
        return ResponseEntity.ok(shareService.getContent(directoryUUID));
    }

}
