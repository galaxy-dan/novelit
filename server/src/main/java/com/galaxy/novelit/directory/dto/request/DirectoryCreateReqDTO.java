package com.galaxy.novelit.directory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DirectoryCreateReqDTO {
	private String name;
	private String workspaceUUID;
	private String parentUUID;
	private boolean directory;
	private String uuid;
}
