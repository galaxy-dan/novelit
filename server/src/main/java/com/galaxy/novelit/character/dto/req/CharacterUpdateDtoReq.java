package com.galaxy.novelit.character.dto.req;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CharacterUpdateDtoReq {
    private String groupUUID;
    private String characterName;
    private String description;
    private List<Map<String, String>> information;
    private List<Map<Map<String, String>,String>> relationship;
    private String characterImage;
}
