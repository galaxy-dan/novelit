package com.galaxy.novelit.words.dto.res;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class WordsDtoRes {
    private String workspaceUuid;
    private String wordUuid;
    private String word;
    private boolean isCharacter;
}
