package com.galaxy.novelit.directory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galaxy.novelit.directory.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {
	File findByDirectoryUUID(String directoryUUID);
}
