package com.galaxy.novelit.character.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CharacterSimpleDtoRes {
    private String characterUUID;
    private String characterName;
}
