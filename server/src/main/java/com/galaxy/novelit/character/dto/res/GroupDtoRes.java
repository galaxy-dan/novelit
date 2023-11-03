package com.galaxy.novelit.character.dto.res;

import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupDtoRes {
    private String workspaceUUID;
    private String groupUUID;
    private String groupName;
    private String parentUUID;
    private List<GroupEntity> childUUID;
    private List<Map<String, String>> charactersInfo;
    private boolean isDeleted;
}
