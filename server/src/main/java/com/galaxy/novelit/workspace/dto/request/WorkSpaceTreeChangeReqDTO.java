package com.galaxy.novelit.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkSpaceTreeChangeReqDTO {
	private String workspaceUUID;
	private String directoryUUID;
	private String parentUUID;
	private String nextUUID;

}
