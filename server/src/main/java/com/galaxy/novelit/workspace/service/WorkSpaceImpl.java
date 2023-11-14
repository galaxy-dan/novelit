package com.galaxy.novelit.workspace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.novelit.common.exception.AccessRefusedException;
import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.common.exception.NoSuchWorkspaceException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.directory.service.DirectoryService;
import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import com.galaxy.novelit.workspace.dto.request.WorkSpaceTreeChangeReqDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceElementDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceInfoResDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceResDTO;
import com.galaxy.novelit.workspace.mapper.WorkspaceMapper;
import com.galaxy.novelit.workspace.repository.WorkspaceRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkSpaceImpl implements WorkspaceService{

    private final WorkspaceMapper workspaceMapper;
    private final DirectoryService directoryService;
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

        Directory root = Directory.builder()
            .uuid(workSpaceUUID)
            .directory(true)
            .userUUID(user_uuid)
            .deleted(false)
            .children(new ArrayList<>())
            .build();
        directoryRepository.save(root);
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
            directoryService.deleteDirectory(workSpaceUUID, ws.getUserUUID());
        } else {
            throw new IllegalStateException("없는 작품 입니다.");
        }
    }

    @Override
    public WorkSpaceInfoResDTO getWorkspaceInfo(String workSpaceUUID) {
        Workspace workspace = workspaceRepository.findByWorkspaceUUID(workSpaceUUID).orElseThrow(
            NoSuchWorkspaceException::new);

        List<WorkSpaceElementDTO> directories = directoryRepository.findAllByParentUUIDAndDeleted(workSpaceUUID,
                false).stream()
            .map(workspaceMapper::toElementDto)
            .toList();

        return new WorkSpaceInfoResDTO(workspace.getTitle(), directories);

    }
    @Override
    public List<?> getWorkspaces(String userUUID) {

        List<Workspace> workspaceList = workspaceRepository.findAllByUserUUID(userUUID);

        List<WorkSpaceResDTO> workspaceNames = new ArrayList<>();

        for (Workspace ws : workspaceList) {
            workspaceNames.add(new WorkSpaceResDTO(ws.getWorkspaceUUID(), ws.getTitle()));
        }
        return workspaceNames;

    }

    @Transactional
    @Override
    public void changeTree(WorkSpaceTreeChangeReqDTO dto, String userUUID) {
        String workspaceUUID = dto.getWorkspaceUUID();
        Workspace workspace = workspaceRepository.findByWorkspaceUUID(workspaceUUID).orElseThrow(()->new IllegalStateException("없는 작품 입니다."));
        //권한 예외 처리
        if(!workspace.getUserUUID().equals(userUUID)){
            throw new AccessRefusedException();
        }

        String directoryUUID = dto.getDirectoryUUID();
        Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);

        //새로운 부모 예외 처리
        String parentUUID = dto.getParentUUID();
        Directory parent = parentUUID == null ? directoryRepository.findByUuidAndDeleted(workspaceUUID, false) : directoryRepository.findByUuidAndDeleted(parentUUID, false);
        checkDirectoryException(parent, userUUID, workspaceUUID);

        checkDirectoryException(directory, userUUID, workspaceUUID);

        //이전 부모 예외 처리
        String lastParentUUID = directory.getParentUUID();
        Directory lastParent = lastParentUUID == null ? directoryRepository.findByUuidAndDeleted(workspaceUUID, false) : directoryRepository.findByUuidAndDeleted(lastParentUUID, false);
        checkDirectoryException(lastParent, userUUID, workspaceUUID);

        //이전 부모 children에서 삭제
        List<String> lastChildren = lastParent.getChildren();
        lastChildren.remove(directoryUUID);
        lastParent.updateChildren(lastChildren);
        directoryRepository.save(lastParent);

        //새로운 부모 children에 추가
        if(lastParent.equals(parent)){
            parent = lastParent;
        }
        String nextUUID = dto.getNextUUID();
        List<String> children = parent.getChildren();
        if(nextUUID == null){
            children.add(directoryUUID);
        }else{
            int index = 0;
            for (String d : children) {
                if(d.equals(nextUUID)){
                    break;
                }
                index++;
            }
            children.add(index, directoryUUID);
        }
        parent.updateChildren(children);
        directoryRepository.save(parent);

        directory.updateParentUUID(parent.getUuid());
        directoryRepository.save(directory);
    }

    private void checkDirectoryException(Directory directory, String userUUID, String workspaceUUID){
        //404 예외 처리
        if(directory == null){
            throw new NoSuchDirectoryException();
        }

        //권한 예외 처리
        if(!directory.getUserUUID().equals(userUUID)){
            throw new AccessRefusedException();
        }

        if(directory.getWorkspaceUUID() != null && !directory.getWorkspaceUUID().equals(workspaceUUID)){
            throw new AccessRefusedException();
        }
    }
}
