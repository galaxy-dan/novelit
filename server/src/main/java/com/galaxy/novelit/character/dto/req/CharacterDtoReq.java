package com.galaxy.novelit.character.dto.req;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CharacterDtoReq {
    private String userUuid;
    private String groupUuid;
    private String characterUuid;
    private String characterName;
    private String description;
    private Map<String, String> information;
    private Map<String, String> relationship;
    private boolean isDeleted;
}
