package com.galaxy.novelit.workspace.controller;

import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public void createWorkspace() {

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
