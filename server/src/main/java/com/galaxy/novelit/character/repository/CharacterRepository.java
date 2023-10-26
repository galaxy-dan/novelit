package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.CharacterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterUuid(String characterUuid);
}
