package com.galaxy.novelit.directory.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.novelit.directory.domain.Directory;

@Repository
public interface DirectoryRepository extends MongoRepository<Directory, String> {
	Directory findByUuidAndDeleted(String uuid, boolean deleted);
	boolean existsByUuidAndDeleted(String uuid, boolean deleted);

	Optional<Directory> findDirectoryByUuid(String directoryUUID);
}
