package com.galaxy.novelit.workspace.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceInfoResDTO {
	private String title;
	private List<WorkSpaceElementDTO> directories;
}
