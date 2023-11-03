package com.galaxy.novelit.words.service;

import com.galaxy.novelit.words.dto.res.WordsDtoRes;
import com.galaxy.novelit.words.repository.WordsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WordsServiceImpl implements WordsService {
//    private final WordsRepository wordsRepository;

    @Transactional(readOnly = true)
    @Override
    public WordsDtoRes getWords(String workspaceUuid) {
        WordsDtoRes dto = new WordsDtoRes();


        return dto;
    }

    @Override
    public void createWord(String workspaceUuid, String word) {

    }

    @Override
    public void updateWord(String wordUuid) {

    }

    @Override
    public void deleteWord(String wordUuid) {

    }
}
