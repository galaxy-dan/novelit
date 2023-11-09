package com.galaxy.novelit.share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.share.dto.request.EditableReqDTO;
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

    @GetMapping("/token")
    public ResponseEntity<ShareTokenResDTO> generateToken(@RequestParam("directoryUUID") String directoryUUID, Authentication authentication) {
        return ResponseEntity.ok(shareService.generateToken(directoryUUID, authentication.getName()));
    }

    @PatchMapping("/toggle")
    public ResponseEntity<Void> updateEditable(@RequestBody EditableReqDTO dto, Authentication authentication) {
        shareService.updateEditable(dto, authentication.getName());
        return ResponseEntity.ok().build();
    }

}
