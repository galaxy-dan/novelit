package com.galaxy.novelit.directory.service;

import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;

public interface DirectoryService {
	void createDirectory(DirectoryCreateReqDTO dto, String userUUID);
}
