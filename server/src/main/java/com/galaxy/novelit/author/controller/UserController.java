package com.galaxy.novelit.author.controller;

import com.galaxy.novelit.author.dto.response.AuthorAndWorkspaceResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<AuthorAndWorkspaceResDTO> getAuthorInfoAndWorkspace() {
//        return ResponseEntity.ok();
        return null;
    }
}
