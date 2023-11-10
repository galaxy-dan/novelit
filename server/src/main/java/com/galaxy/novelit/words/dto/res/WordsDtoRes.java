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
    private List<wordInfo> wordInfo;

    @Getter
    @Setter
    public static class wordInfo {
        String wordUUID;
        String word;
        String isCharacter;
    }
}
