package com.galaxy.novelit.character.dto.req;

import java.util.Map;

public class CharacterDtoReq {
    private Long characterId;
    private String userUuid;
    private String groupUuid;
    private String characterUuid;
    private String characterName;
    private String description;
    private Map<String, String> information;
    private Map<String, String> relationship;
    private boolean isDeleted;
}
