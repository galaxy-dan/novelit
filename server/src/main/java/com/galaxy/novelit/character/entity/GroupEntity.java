package com.galaxy.novelit.character.entity;

import jakarta.persistence.Id;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "groups")
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
    private String parentUUID;
    @DBRef
    private List<GroupEntity> childUUID;
    @Field(name = "characters_uuid")
    private List<String> charactersUUID;
//    @Field
//    private Map<String, String>
    @Field(name = "is_deleted")
    private boolean isDeleted;
}