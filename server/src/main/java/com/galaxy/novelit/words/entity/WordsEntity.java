package com.galaxy.novelit.words.entity;

import jakarta.persistence.Entity;
import java.util.UUID;

@Entity(name = "words")
public class WordsEntity {
    private UUID workspaceUuid;
    private UUID wordUuid;
    private String word;
    private boolean isCharacter;
}
