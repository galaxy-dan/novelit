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
    private String userUUID;
    private String workspaceUUID;
    private String groupUUID;
    private String groupName;
    private String parentUUID;
    private List<GroupEntity> childUUID;
    private boolean isDeleted;
    private List<String> charactersUUID;

}
