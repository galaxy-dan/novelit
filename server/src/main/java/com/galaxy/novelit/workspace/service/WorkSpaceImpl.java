package com.galaxy.novelit.workspace.service;

import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceElementDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceInfoResDTO;
import com.galaxy.novelit.workspace.mapper.WorkspaceMapper;
import com.galaxy.novelit.workspace.repository.WorkspaceRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkSpaceImpl implements WorkspaceService{

    private final WorkspaceMapper workspaceMapper;

    private final WorkspaceRepository workspaceRepository;
    private final EntityManager em;
    private final DirectoryRepository directoryRepository;

    @Override
    @Transactional
    public void createWorkspace(String workSpaceUUID, String user_uuid, String title) {
        Workspace workspace = workspaceMapper.toEntity(WorkSpaceDTO.builder()
            .workspaceUUID(workSpaceUUID)
            .title(title)
            .userUUID(user_uuid)
            .build());

        workspaceRepository.save(workspace);
    }

    @Override
    @Transactional
    public void modifyWorkspaceName(String workSpaceUUID, String title) {
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceUUID(workSpaceUUID);

        if (workspace.isPresent()) {
            Workspace ws = workspace.get();
            WorkSpaceDTO workspace1 = WorkSpaceDTO.builder()
                .id(ws.getId())
                .title(title)
                .userUUID(ws.getUserUUID())
                .workspaceUUID(ws.getWorkspaceUUID())
                .build();

            workspaceRepository.save(workspaceMapper.toEntity(workspace1));
        } else {
            throw new IllegalStateException("없는 작품 입니다.");
        }
    }

    @Override
    public void deleteWorkspace(String workSpaceUUID) {
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceUUID(workSpaceUUID);

        if (workspace.isPresent()) {
            Workspace ws = workspace.get();
            workspaceRepository.delete(ws);
        } else {
            throw new IllegalStateException("없는 작품 입니다.");
        }
    }

    @Override
    public WorkSpaceInfoResDTO getWorkspaceInfo(String workSpaceUUID) {
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceUUID(workSpaceUUID);
        if(workspace.isPresent()){
            Workspace ws = workspace.get();
            List<WorkSpaceElementDTO> directories = directoryRepository.findByParentUUIDAndDeletedAndWorkspaceUUID(null, false, workSpaceUUID).stream()
                .map(workspaceMapper::toElementDto)
                .toList();

            return new WorkSpaceInfoResDTO(ws.getTitle(), directories);
        }else {
            throw new IllegalStateException("없는 작품 입니다.");
        }
    }
}
