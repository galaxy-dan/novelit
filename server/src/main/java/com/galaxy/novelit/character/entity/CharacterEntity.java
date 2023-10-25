package com.galaxy.novelit.character.entity;

import jakarta.persistence.Entity;

@Entity(name = "character")
public class CharacterEntity {
    private String workspace;
    private String name;
    private String description;
    private String information;
    private String relationship;

}
