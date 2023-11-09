package com.galaxy.novelit.character.dto.res;

import com.galaxy.novelit.character.entity.RelationEntity;
import com.galaxy.novelit.character.entity.RelationEntity.Relation;
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
@Builder
public class RelationDtoRes {
    private String characterUUID;
    private String characterName;
    private String groupUUID;
    private String groupName;
    private List<RelationDto> relations;

    @Getter
    @Setter
    @Builder
    public static class RelationDto {
        private String targetUUID;
        private String targetName;
        private String targetGroupUUID;
        private String targetGroupName;
        private String content;
    }
}
