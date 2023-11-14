package com.galaxy.novelit.character.dto.res;

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
    private String characterImage;
    private Map<String, Double> characterNode;
    private String groupUUID;
    private String groupName;
    private Map<String, Double> groupNode;
    private List<RelationDto> relations;

    @Getter
    @Setter
    @Builder
    public static class RelationDto {
        private String targetUUID;
        private String targetName;
        private String targetImage;
        private String targetGroupUUID;
        private String targetGroupName;
        private String content;
    }
}
