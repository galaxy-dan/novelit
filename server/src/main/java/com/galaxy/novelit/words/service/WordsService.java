package com.galaxy.novelit.words.service;

import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.dto.req.WordsUpdateReqDTO;
import com.galaxy.novelit.words.dto.res.WordsDtoRes;

public interface WordsService {
    WordsDtoRes getWords(String workspaceUUID);
    void createWord(WordsCreateReqDTO dto, String userUUID);
    void updateWord(String wordUUID, String newWord);
    void deleteWord(String wordUUID);
}
