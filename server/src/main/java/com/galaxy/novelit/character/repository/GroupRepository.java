package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<GroupEntity, String> {
    GroupEntity findByGroupUUID(String groupUUID);
    GroupEntity findByGroupUUIDAndWorkspaceUUID(String groupUUID, String workspaceUUID);
    List<GroupEntity> findAllByParentGroupUUID(String parentGroupUUID);
    List<GroupEntity> findAllByParentGroupUUIDAndDeletedIsFalse(String parentGroupUUID);
    List<GroupEntity> findAllByWorkspaceUUIDAndParentGroupUUIDIsNull(String workspaceUUID);
    List<GroupEntity> findAllByWorkspaceUUIDAndParentGroupUUIDIsNullAndDeletedIsFalse(String workspaceUUID);
    List<GroupEntity> findAllByWorkspaceUUIDAndDeletedIsFalse(String workspaceUUID);
}
