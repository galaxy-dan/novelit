package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<GroupEntity, String> {
    GroupEntity findByGroupUUID(String groupUUID);
    List<GroupEntity> findAllByParentGroupUUID(String parentGroupUUID);
    List<GroupEntity> findAllByWorkspaceUUIDAndParentGroupUUIDIsNull(String workspaceUUID);
}
