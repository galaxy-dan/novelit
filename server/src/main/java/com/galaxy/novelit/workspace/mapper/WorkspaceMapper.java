package com.galaxy.novelit.workspace.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import com.galaxy.novelit.workspace.dto.response.WorkSpaceElementDTO;

@Component
@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    Workspace toEntity(WorkSpaceDTO workSpaceDTO);
    WorkSpaceDTO toDto(Workspace workspace);
    default WorkSpaceElementDTO toElementDto(Directory directory){
        List<String> dirList = directory.getChildren();

        List<WorkSpaceElementDTO> dtoList = dirList == null ? null : dirList.stream()
            .filter(d->!d.isDeleted()).map(this::toElementDto).toList();
        return new WorkSpaceElementDTO(directory.getUuid(), directory.getName(), directory.isDirectory(), dtoList);
    }
}
