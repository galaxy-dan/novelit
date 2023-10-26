package com.galaxy.novelit.directory.service;

import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;

public interface DirectoryService {
	void createDirectory(DirectoryCreateReqDTO dto, String userUUID);
	void editDirectoryName(DirectoryNameEditReqDTO dto, String userUUID);
}
