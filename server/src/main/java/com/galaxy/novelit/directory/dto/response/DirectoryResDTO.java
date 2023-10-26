package com.galaxy.novelit.directory.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DirectoryResDTO {
	private List<DirectorySimpleElementDTO> directories;
	private List<DirectorySimpleElementDTO> files;
}
