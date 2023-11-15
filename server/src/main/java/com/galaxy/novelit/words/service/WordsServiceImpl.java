package com.galaxy.novelit.words.service;

import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.dto.req.WordsUpdateReqDTO;
import com.galaxy.novelit.words.dto.res.WordsDtoRes;
import com.galaxy.novelit.words.dto.res.WordsDtoRes.WordInfo;
import com.galaxy.novelit.words.entity.WordsEntity;
import com.galaxy.novelit.words.repository.WordsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordsServiceImpl implements WordsService {

    private final WordsRepository wordsRepository;

    @Transactional(readOnly = true)
    @Override
    public WordsDtoRes getWords(String workspaceUUID) {
        WordsDtoRes dto = new WordsDtoRes();
        List<WordsEntity> wordList = wordsRepository.findAllByWorkspaceUUID(workspaceUUID);
        List<WordInfo> wordInfoList = new ArrayList<>();

        for (WordsEntity word : wordList) {
            WordInfo wordInfo = new WordInfo();
            wordInfo.setWordUUID(word.getWordUUID());
            wordInfo.setWord(word.getWord());
            wordInfo.setCharacter(word.isCharacter());

            wordInfoList.add(wordInfo);
        }

        dto.setWorkspaceUUID(workspaceUUID);
        dto.setWordInfo(wordInfoList);

        return dto;
    }

    @Override
    @Transactional
    public void createWord(WordsCreateReqDTO dto, String uuid, String userUUID) {
        String wordUUID = UUID.randomUUID().toString();
        WordsEntity word = WordsEntity.builder()
            .userUUID(userUUID)
            .workspaceUUID(dto.getWorkspaceUUID())
            .wordUUID(uuid)
            .word(dto.getWord())
            .build();

        wordsRepository.save(word);
    }

    @Override
    @Transactional
    public void updateWord(String wordUUID, String newWord) {
//        WordsEntity wordsEntity = wordsRepository.findByWordUuid(dto.getWordUUID())
//            .orElseThrow(() -> new NoSuchElementFoundException("없는 단어 입니다."));
//
//        WordsEntity newWordsEntity = wordsEntity.builder()
//            .wordId(wordsEntity.getWordId())
//            .wordUuid(wordsEntity.getWordUuid())
//            .isCharacter(wordsEntity.isCharacter())
//            .workspaceUuid(wordsEntity.getWorkspaceUuid())
//            .word(dto.getWord())
//            .build();

        WordsEntity word = wordsRepository.findByWordUUID(wordUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("없는 단어 입니다."));
        word.updateWord(newWord);

        wordsRepository.save(word);
    }

    @Override
    @Transactional
    public void deleteWord(String wordUUID) {
        wordsRepository.deleteByWordUUID(wordUUID);
    }
}
