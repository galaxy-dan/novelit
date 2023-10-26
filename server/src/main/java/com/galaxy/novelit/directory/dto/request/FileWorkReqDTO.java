package com.galaxy.novelit.directory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileWorkReqDTO {
	private String uuid;
	private String content;
}
