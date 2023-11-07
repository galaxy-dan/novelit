package com.galaxy.novelit.words.service;

import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.dto.req.WordsUpdateReqDTO;
import com.galaxy.novelit.words.dto.res.WordsDtoRes;

public interface WordsService {
    WordsDtoRes getWords(String workspaceUuid);
    void createWord(WordsCreateReqDTO dto);
    void updateWord(WordsUpdateReqDTO wordsUpdateReqDTO);
    void deleteWord(String wordUuid);
}
