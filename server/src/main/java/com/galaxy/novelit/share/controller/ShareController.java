package com.galaxy.novelit.share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.share.dto.response.ShareTokenResDTO;
import com.galaxy.novelit.share.service.ShareService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/share")
@RequiredArgsConstructor
@RestController
public class ShareController {

    private final ShareService shareService;

    // @GetMapping
    // public ResponseEntity<String> shareContent(@RequestParam("directoryUUID") String directoryUUID) {
    //     return ResponseEntity.ok(shareService.getContent(directoryUUID));
    // }

    @GetMapping("")
    public ResponseEntity<ShareTokenResDTO> generateToken(@RequestParam("directoryUUID") String directoryUUID) {
        return ResponseEntity.ok(shareService.generateToken(directoryUUID));
    }

}
