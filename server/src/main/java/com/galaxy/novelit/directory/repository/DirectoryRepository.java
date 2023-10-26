package com.galaxy.novelit.directory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.novelit.directory.domain.Directory;

@Repository
public interface DirectoryRepository extends MongoRepository<Directory, String> {
	Directory findByUuid(String uuid);
}
