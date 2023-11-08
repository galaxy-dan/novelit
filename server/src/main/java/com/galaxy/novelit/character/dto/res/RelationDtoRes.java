package com.galaxy.novelit.character.dto.res;

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
public class RelationDtoRes {
    private String start;
    private List<List<String>> end;     //List<[이름,내용,이미지]>
    private String content;
}
