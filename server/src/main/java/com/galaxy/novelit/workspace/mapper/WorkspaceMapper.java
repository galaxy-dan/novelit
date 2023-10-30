package com.galaxy.novelit.workspace.mapper;

import com.galaxy.novelit.workspace.domain.Workspace;
import com.galaxy.novelit.workspace.dto.WorkSpaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    Workspace toEntity(WorkSpaceDTO workSpaceDTO);
    WorkSpaceDTO toDto(Workspace workspace);
}
