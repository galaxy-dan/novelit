package com.galaxy.novelit.directory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DirectoryNameEditReqDTO {
	private String uuid;
	private String name;
}
