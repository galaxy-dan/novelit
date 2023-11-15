package com.galaxy.novelit.words.dto.res;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WordsDtoRes {
    private String workspaceUUID;
    private List<WordInfo> wordInfo;

    @Getter
    @Setter
    public static class WordInfo {
        String wordUUID;
        String word;
        boolean isCharacter;
    }
}
