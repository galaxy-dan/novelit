package com.galaxy.novelit.author.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.author.dto.response.AuthorAndWorkspaceResDTO;
import com.galaxy.novelit.author.service.UserService;
import com.galaxy.novelit.workspace.service.WorkspaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<AuthorAndWorkspaceResDTO> getAuthorInfoAndWorkspace(Authentication authentication) {
        String userUUID = authentication.getName();
        String nickname = userService.getUserNickname(userUUID);

        // 작가의 작품리스트를 가져옴
//        List<?> workspaces = workspaceService.getWorkspaces(testUUID);
        List<?> workspaces = workspaceService.getWorkspaces(userUUID);

        AuthorAndWorkspaceResDTO authorAndWorkspaceResDTO = AuthorAndWorkspaceResDTO.builder()
            .nickname(nickname).workspaces(workspaces).build();

        System.out.println(authorAndWorkspaceResDTO.getNickname());
        System.out.println(authorAndWorkspaceResDTO.getWorkspaces());
        return ResponseEntity.ok(authorAndWorkspaceResDTO);
    }
}
