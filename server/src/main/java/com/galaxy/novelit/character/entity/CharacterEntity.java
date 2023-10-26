package com.galaxy.novelit.character.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CharacterEntity {
    private Long characterId;
    private UUID groupUuid;
    private UUID characterUuid;
    private String characterName;
    private String description;
    private String information;
    private String relationship;
    private boolean isDeleted;

}
