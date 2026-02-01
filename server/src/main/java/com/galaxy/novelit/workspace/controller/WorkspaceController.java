package com.galaxy.novelit.workspace.controller;

import com.galaxy.novelit.workspace.dto.request.WorkSpaceCreateReqDTO;
import com.galaxy.novelit.workspace.dto.request.WorkSpaceModifiedReqDTO;
import com.galaxy.novelit.workspace.dto.request.WorkSpaceTreeChangeReqDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceInfoResDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceResDTO;
import com.galaxy.novelit.workspace.service.WorkspaceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<?> createWorkspace(@RequestBody WorkSpaceCreateReqDTO
            workSpaceCreateReqDTO, String uuid) {
        String workSpaceUUID = String.valueOf(UUID.randomUUID());
        String userUUID = uuid;
        //String userUUID = authentication.getName();
        String title = workSpaceCreateReqDTO.getTitle();
        workspaceService.createWorkspace(workSpaceUUID, userUUID, title);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body( WorkSpaceResDTO.builder().workspaceUUID(workSpaceUUID).build());

    }

    @PatchMapping
    public void modifiedWorkspaceName(@RequestBody WorkSpaceModifiedReqDTO workSpaceModifiedReqDTO,
            Authentication authentication) {
        String userUUID = authentication.getName();
        String workSpaceUUID = workSpaceModifiedReqDTO.getWorkspaceUUID();
        String title = workSpaceModifiedReqDTO.getTitle();

        workspaceService.modifyWorkspaceName(workSpaceUUID, title);

    }

    @DeleteMapping
    public void deleteWorkspace(@RequestParam String workspaceUUID) {
//        String workspaceUUID = workSpaceModifiedReqDTO.getWorkspaceUUID();
        workspaceService.deleteWorkspace(workspaceUUID);
    }

    @GetMapping
    public ResponseEntity<WorkSpaceInfoResDTO> getWorkspaceInfo(@RequestParam String workspaceUUID) {
        return ResponseEntity.ok(workspaceService.getWorkspaceInfo(workspaceUUID));
    }

    @PatchMapping("/tree")
    public ResponseEntity<Void> changeTree(@RequestBody WorkSpaceTreeChangeReqDTO dto,
            Authentication authentication){
        String userUUID = authentication.getName();
        workspaceService.changeTree(dto, userUUID);
        return ResponseEntity.ok().build();
    }
}
