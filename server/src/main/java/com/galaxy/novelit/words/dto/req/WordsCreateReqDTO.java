package com.galaxy.novelit.words.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class WordsCreateReqDTO {
    String workspaceUUID;
    String word;
}
