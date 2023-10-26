package com.galaxy.novelit.directory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.galaxy.novelit.directory.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {
	File findByDirectoryUUIDAndDeleted(String directoryUUID, boolean deleted);
	@Modifying
	@Query("UPDATE File f SET f.deleted = :newValue WHERE f.directoryUUID IN :list")
	void updateDeleted(@Param("list") List<String> list, @Param("newValue") boolean newValue);
}
