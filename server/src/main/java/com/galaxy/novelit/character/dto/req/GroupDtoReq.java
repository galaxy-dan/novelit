package com.galaxy.novelit.character.dto.req;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;

public class GroupDtoReq {
    private Long groupId;
    private String userUuid;
    private String workspaceUuid;
    private String groupUuid;
    private String groupName;
    private String parentUuid;
    private List<GroupEntity> childUuid;
    private boolean isDeleted;
}
