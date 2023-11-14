package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.CharacterEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends MongoRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterUUID(String characterUUID);
    CharacterEntity findByCharacterUUIDAndWorkspaceUUID(String characterUUID, String workspaceUUID);
    List<CharacterEntity> findAllByGroupUUID(String groupUUID);
    List<CharacterEntity> findAllByGroupUUIDAndDeletedIsFalse(String groupUUID);

    List<CharacterEntity> findAllByCharacterName(String characterName);
    List<CharacterEntity> findAllByWorkspaceUUIDAndGroupUUIDIsNull(String workspaceUUID);
    List<CharacterEntity> findAllByWorkspaceUUIDAndGroupUUIDIsNullAndDeletedIsFalse(String workspaceUUID);
    List<CharacterEntity> findAllByWorkspaceUUIDAndCharacterNameLike(String workspaceUUID, String characterName);
    List<CharacterEntity> findAllByWorkspaceUUIDAndDeletedIsFalseAndCharacterNameLike(String workspaceUUID, String characterName);
    List<CharacterEntity> findAllByWorkspaceUUIDAndDeletedIsFalse(String workspaceUUID);
}
