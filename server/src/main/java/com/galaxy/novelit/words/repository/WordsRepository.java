package com.galaxy.novelit.words.repository;

import com.galaxy.novelit.words.entity.WordsEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<WordsEntity,Long> {
    Optional<WordsEntity> findByWordUUID(String wordUUID);
    void deleteByWordUUID(String wordUUID);

    WordsEntity findByUserUUIDAndWorkspaceUUIDAndWord(String userUUID, String workspaceUUID, String characterName);

    List<WordsEntity> findAllByWorkspaceUUID(String workspaceUUID);
}
