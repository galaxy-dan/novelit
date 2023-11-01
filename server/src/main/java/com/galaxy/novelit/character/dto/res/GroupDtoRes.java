package com.galaxy.novelit.character.dto.res;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GroupDtoRes {
    private String userUuid;
    private String workspaceUuid;
    private String groupUuid;
    private String groupName;
    private String parentUuid;
    private List<GroupEntity> childUuid;
    private boolean isDeleted;
}
