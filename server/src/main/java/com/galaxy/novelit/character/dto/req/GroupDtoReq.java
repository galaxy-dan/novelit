package com.galaxy.novelit.character.dto.req;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupDtoReq {
    private String userUUID;
    private String workspaceUUID;
    private String groupUUID;
    private String groupName;
    private String parentUUID;
    private List<GroupEntity> childUUID;
    private boolean isDeleted;
}
