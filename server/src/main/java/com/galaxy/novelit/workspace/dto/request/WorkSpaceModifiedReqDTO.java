package com.galaxy.novelit.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceModifiedReqDTO {
    String workspaceUUID;
    String title;
}
