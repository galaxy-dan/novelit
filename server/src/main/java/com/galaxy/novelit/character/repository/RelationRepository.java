package com.galaxy.novelit.character.repository;

import com.galaxy.novelit.character.entity.RelationEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends MongoRepository<RelationEntity, String> {
    RelationEntity findByCharacterUUID(String characterUUID);
    @Override
    List<RelationEntity> findAll();
}
