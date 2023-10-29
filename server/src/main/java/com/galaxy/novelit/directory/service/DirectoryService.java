package com.galaxy.novelit.directory.service;

import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
import com.galaxy.novelit.directory.dto.response.DirectoryResDTO;
import com.galaxy.novelit.directory.dto.response.FileResDTO;

public interface DirectoryService {
	void createDirectory(DirectoryCreateReqDTO dto, String userUUID);
	void editDirectoryName(DirectoryNameEditReqDTO dto, String userUUID);
	DirectoryResDTO getDirectory(String directoryUUID, String userUUID);
	FileResDTO getFile(String directoryUUID, String userUUID);
	void deleteDirectory(String directoryUUID, String userUUID);
	void workFile(FileWorkReqDTO dto, String userUUID);
}
