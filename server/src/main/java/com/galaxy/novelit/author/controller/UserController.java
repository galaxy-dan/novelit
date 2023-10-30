package com.galaxy.novelit.author.controller;

import com.galaxy.novelit.author.dto.UserDto;
import com.galaxy.novelit.author.dto.response.AuthorAndWorkspaceResDTO;
import com.galaxy.novelit.author.service.UserService;
import com.galaxy.novelit.workspace.service.WorkspaceService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<AuthorAndWorkspaceResDTO> getAuthorInfoAndWorkspace() {
        String testUUID = "f72a8efc-99dc-4afd-a658-6f42073fb7a3";
        String nickname = userService.getUserNickname(testUUID);

        // 작가의 작품리스트를 가져옴
//        List<?> workspaces = workspaceService.getWorkspaces(testUUID);
        List<?> workspaces = workspaceService.getWorkspaces(testUUID);

        AuthorAndWorkspaceResDTO authorAndWorkspaceResDTO = AuthorAndWorkspaceResDTO.builder()
            .nickname(nickname).workspaces(workspaces).build();

        System.out.println(authorAndWorkspaceResDTO.getNickname());
        System.out.println(authorAndWorkspaceResDTO.getWorkspaces());
        return ResponseEntity.ok(authorAndWorkspaceResDTO);
//        return null;
    }
}
