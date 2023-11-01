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
    private String userUuid;
    private String workspaceUuid;
    private String groupUuid;
    private String groupName;
    private String parentUuid;
    private List<GroupEntity> childUuid;
    private boolean isDeleted;
}
