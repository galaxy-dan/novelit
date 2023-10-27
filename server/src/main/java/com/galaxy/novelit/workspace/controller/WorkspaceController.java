package com.galaxy.novelit.workspace.controller;

import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.request.WorkSpaceCreateReqDTO;
import com.galaxy.novelit.workspace.service.WorkspaceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public void createWorkspace(@RequestBody WorkSpaceCreateReqDTO workSpaceCreateReqDTO) {
        String workSpaceUUID = String.valueOf(UUID.randomUUID());
        String testUUID = "f72a8efc-99dc-4afd-a658-6f42073fb7a3";
        String title = workSpaceCreateReqDTO.getTitle();
        workspaceService.createWorkspace(workSpaceUUID, testUUID, title);
    }

    @PatchMapping
    public void modifiedWorkspaceName() {

    }

    @DeleteMapping
    public void deleteWorkspace() {

    }

    @GetMapping
    public void getWorkspaceInfo() {

    }
}
