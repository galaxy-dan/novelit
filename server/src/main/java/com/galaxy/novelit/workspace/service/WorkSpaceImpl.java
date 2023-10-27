package com.galaxy.novelit.workspace.service;

import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import com.galaxy.novelit.workspace.mapper.WorkspaceMapper;
import com.galaxy.novelit.workspace.repository.WorkspaceRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkSpaceImpl implements WorkspaceService{

    private final WorkspaceMapper workspaceMapper;

    private final WorkspaceRepository workspaceRepository;
    private final EntityManager em;

    @Override
    public void createWorkspace(String workSpaceUUID, String user_uuid, String title) {
        Workspace workspace = workspaceMapper.toEntity(WorkSpaceDTO.builder()
            .workspaceUUID(workSpaceUUID)
            .title(title)
            .userUUID(user_uuid)
            .build());

        workspaceRepository.save(workspace);
    }
}
