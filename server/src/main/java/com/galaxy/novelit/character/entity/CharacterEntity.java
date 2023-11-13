package com.galaxy.novelit.character.entity;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "character")
@Getter
@Setter
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
    private boolean deleted;
    @Field(name = "character_image")
    private String characterImage;
    @Field(name = "character_node")
    private Map<String, Double> characterNode;

    public void deleteCharacter() {
        this.deleted = true;
    }
    public void moveCharacter(String groupUUID) {
        this.groupUUID = groupUUID;
    }
    public void moveCharacterNode(Double x, Double y) {
        this.characterNode.replace("x", x);
        this.characterNode.replace("y", y);
    }

}
