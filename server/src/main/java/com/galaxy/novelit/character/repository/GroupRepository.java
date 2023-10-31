package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.GroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<GroupEntity, String> {
    GroupEntity findByGroupUuid(String groupUuid);
}
