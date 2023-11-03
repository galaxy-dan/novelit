package com.galaxy.novelit.words.repository;

import com.galaxy.novelit.words.entity.WordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<WordsEntity,Long> {
    WordsEntity findByWordUuid(String wordUuid);
}
