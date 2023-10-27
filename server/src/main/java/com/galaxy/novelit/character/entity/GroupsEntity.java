package com.galaxy.novelit.character.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "groups")
public class GroupsEntity {
    @Id
    @Column(name = "group_id")
    private Long groupId;
    @Id
    @Column(name = "workspace_uuid")
    private UUID workspaceUuid;
    @Column(name = "group_id")
    private UUID groupUuid;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "parent_group")
    private UUID parentGroup;
    @Column(name = "is_deleted")
    private boolean isDeleted;

}