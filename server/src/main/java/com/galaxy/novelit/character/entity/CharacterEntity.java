package com.galaxy.novelit.character.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CharacterEntity {
    private String workspace;
    private String name;
    private String description;
    private String information;
    private String relationship;

}
