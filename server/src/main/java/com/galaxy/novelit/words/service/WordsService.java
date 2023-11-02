package com.galaxy.novelit.words.service;

import com.galaxy.novelit.words.dto.res.WordsDtoRes;

public interface WordsService {
    WordsDtoRes getWords(String workspaceUuid);
    void createWord(String workspaceUuid, String word);
    void updateWord(String wordUuid);
    void deleteWord(String wordUuid);
}
