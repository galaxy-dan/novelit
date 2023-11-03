package com.galaxy.novelit.character.dto.res;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class CharacterSimpleDtoRes {
    private String characterUUID;
    private String characterName;
    private List<Map<String, String>> information;
    private String characterImage;
}
