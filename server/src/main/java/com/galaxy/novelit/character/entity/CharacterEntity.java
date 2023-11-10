package com.galaxy.novelit.character.entity;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @Field(name = "workspace_uuid")
    private String workspaceUUID;
    @Field(name = "group_uuid")
    private String groupUUID;
    @Field(name = "character_uuid")
    private String characterUUID;
    @Field(name = "character_name")
    private String characterName;
    private String description;
    private List<Map<String, String>> information;
    @DBRef
    private RelationEntity relationship;
    @Field(name = "is_deleted")
    private boolean isDeleted;
    @Field(name = "character_image")
    private String characterImage;

    public void deleteCharacter() {
        this.isDeleted = true;
    }

}
