package com.galaxy.novelit.directory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileResDTO {
	private String title;
	private String content;
	private boolean editable;
}
