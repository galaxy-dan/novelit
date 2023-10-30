package com.galaxy.novelit.workspace.mapper;

import java.util.List;

import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceElementDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    Workspace toEntity(WorkSpaceDTO workSpaceDTO);
    WorkSpaceDTO toDto(Workspace workspace);
    default WorkSpaceElementDTO toElementDto(Directory directory){
        List<Directory> dirList = directory.getChildren();
        List<WorkSpaceElementDTO> dtoList = dirList == null ? null : dirList.stream().map(this::toElementDto).toList();
        return new WorkSpaceElementDTO(directory.getUuid(), directory.getName(), directory.isDirectory(),
            directory.getPrevUUID(), directory.getNextUUID(), dtoList);
    }
}
