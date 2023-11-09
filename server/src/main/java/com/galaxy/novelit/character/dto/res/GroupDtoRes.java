package com.galaxy.novelit.character.dto.res;

import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.GroupEntity;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String parentGroupUUID;
    private List<GroupEntity> childGroups;
    private List<CharacterEntity> childCharacters;
    private boolean isDeleted;
}
