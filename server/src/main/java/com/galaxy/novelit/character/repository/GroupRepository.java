package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.GroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<GroupEntity, String> {
    GroupEntity findByGroupUuidAndUserUuid(String groupUuid, String userUuid);
}
