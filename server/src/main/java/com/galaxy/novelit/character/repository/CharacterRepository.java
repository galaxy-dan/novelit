package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.CharacterEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends MongoRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterUUID(String characterUUID);
    Optional<List<CharacterEntity>> findAllByGroupUUID(String groupUUID);
}
