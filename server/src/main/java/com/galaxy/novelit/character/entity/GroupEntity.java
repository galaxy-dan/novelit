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

@Document(collection = "group")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntity {
    @Id
    private String groupId;
    @Field(name = "group_uuid")
    private String groupUUID;
    @Field(name = "user_uuid")
    private String userUUID;
    @Field(name = "workspace_uuid")
    private String workspaceUUID;
    @Field(name = "group_name")
    private String groupName;
    @Field(name = "parent_uuid")
    private String parentGroupUUID;
    @DBRef
    private List<GroupEntity> childGroups;
    @DBRef
    private List<CharacterEntity> childCharacters;
//    @DBRef
//    private List<Map<String, String>> charactersInfo;   // Map<characteUUID, characterName>
    @Field(name = "is_deleted")
    private boolean isDeleted;

    public void updateGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void deleteGroup() {
        this.isDeleted = true;
    }

}