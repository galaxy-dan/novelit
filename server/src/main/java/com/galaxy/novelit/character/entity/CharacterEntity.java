package com.galaxy.novelit.character.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "character")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterEntity {
    @Id
    private String characterId;
    @Field(name = "user_uuid")
    private String userUUID;
    @Field(name = "group_uuid")
    private String groupUUID;
    @Field(name = "character_uuid")
    private String characterUUID;
    @Field(name = "character_name")
    private String characterName;
    @Field(name = "description")
    private String description;
    @Field(name = "information")
    private List<Map<String, String>> information;
    @Field(name = "relationship")
    private List<Map<String, String>> relationship;
    @Field(name = "is_deleted")
    private boolean isDeleted;
    @Field(name = "character_image")
    private String characterImage;
//    List<ArrayList<Map<String, String>>>

    public void deleteCharacter() {
        this.isDeleted = true;
    }

}
