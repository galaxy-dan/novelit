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

@Document(collection = "group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntity {
    @Id
    private String groupId;
    @Field(name = "user_uuid")
    private String userUUID;
    @Field(name = "workspace_uuid")
    private String workspaceUUID;
    @Field(name = "group_uuid")
    private String groupUUID;
    @Field(name = "group_name")
    private String groupName;
    @Field(name = "parent_uuid")
    private String parentGroupUUID;
    @DBRef
    private List<GroupEntity> childGroups;
    @DBRef
    private List<CharacterEntity> childCharacters;
    @Field(name = "is_deleted")
    private boolean deleted;
    @Field(name = "group_node")
    private Map<String, Double> groupNode;

    public void updateGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void deleteGroup() {
        this.deleted = true;
    }
    public void addChildGroup(GroupEntity child) {
        this.childGroups.add(child);
    }
    public void removeChildGroup(GroupEntity child) {
        this.childGroups.remove(child);
    }
    public void addChildCharacter(CharacterEntity child) {
        this.childCharacters.add(child);
    }
    public void removeChildCharacter(CharacterEntity child) {
        this.childCharacters.remove(child);
    }
    public void moveGroupNode(Double x, Double y) {
        this.groupNode.replace("x", x);
        this.groupNode.replace("y", y);
    }
}