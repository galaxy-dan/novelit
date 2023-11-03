package com.galaxy.novelit.workspace.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceElementDTO {
	private String uuid;
	private String name;
	private boolean directory;
	private List<WorkSpaceElementDTO> children;

}