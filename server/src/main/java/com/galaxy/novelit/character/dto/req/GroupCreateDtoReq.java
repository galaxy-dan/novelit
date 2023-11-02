package com.galaxy.novelit.character.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupCreateDtoReq {
    private String userUUID;
    private String workspaceUUID;
    private String groupUUID;
    private String groupName;
    private String parentUUID;
}
