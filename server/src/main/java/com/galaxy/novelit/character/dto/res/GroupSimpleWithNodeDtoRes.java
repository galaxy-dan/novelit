package com.galaxy.novelit.character.dto.res;

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
public class GroupSimpleWithNodeDtoRes {
    private String groupUUID;
    private String groupName;
    private Map<String, Double> groupNode;
    private String parentGroupUUID;
}
