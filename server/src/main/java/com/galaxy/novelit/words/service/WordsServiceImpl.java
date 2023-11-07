package com.galaxy.novelit.words.service;

import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.dto.req.WordsUpdateReqDTO;
import com.galaxy.novelit.words.dto.res.WordsDtoRes;
import com.galaxy.novelit.words.entity.WordsEntity;
import com.galaxy.novelit.words.repository.WordsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordsServiceImpl implements WordsService {

    private final WordsRepository wordsRepository;

    @Transactional(readOnly = true)
    @Override
    public WordsDtoRes getWords(String workspaceUuid) {
        WordsDtoRes dto = new WordsDtoRes();


        return dto;
    }

    @Override
    @Transactional
    public void createWord(WordsCreateReqDTO dto) {
        wordsRepository.save(WordsEntity.builder()
            .wordUuid(dto.getWordUUID())
            .word(dto.getWord())
            .isCharacter(dto.getIsCharacter())
            .workspaceUuid(dto.getWorkspaceUUID())
            .build());
    }

    @Override
    @Transactional
    public void updateWord(WordsUpdateReqDTO dto) {
        WordsEntity wordsEntity = wordsRepository.findByWordUuid(dto.getWordUUID())
            .orElseThrow(() -> new NoSuchElementFoundException("없는 단어 입니다."));

        WordsEntity newWordsEntity = wordsEntity.builder()
            .wordId(wordsEntity.getWordId())
            .wordUuid(wordsEntity.getWordUuid())
            .isCharacter(wordsEntity.isCharacter())
            .workspaceUuid(wordsEntity.getWorkspaceUuid())
            .word(dto.getWord())
            .build();


        wordsRepository.save(newWordsEntity);
    }

    @Override
    @Transactional
    public void deleteWord(String wordUuid) {
        wordsRepository.deleteByWordUuid(wordUuid);
    }
}
