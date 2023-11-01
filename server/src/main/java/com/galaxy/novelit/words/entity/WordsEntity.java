package com.galaxy.novelit.words.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "words")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;
    @Column(name = "workspace_id")
    private String workspaceUuid;
    @Column(name = "word_uuid")
    private String wordUuid;
    @Column(name = "word")
    private String word;
    @Column(name = "is_character")
    private boolean isCharacter;
}
