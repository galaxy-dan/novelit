package com.galaxy.novelit.workspace.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkSpaceDTO {
    private Long id;
    private String workspaceUUID;
    private String title;
    private String userUUID;
}
