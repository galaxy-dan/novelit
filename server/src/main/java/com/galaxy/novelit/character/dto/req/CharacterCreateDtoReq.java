package com.galaxy.novelit.character.dto.req;

import com.galaxy.novelit.character.entity.RelationEntity;
import com.galaxy.novelit.character.entity.RelationEntity.Relation;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CharacterCreateDtoReq {
    private String workspaceUUID;
    private String groupUUID;
    private String characterName;
    private String description;
    private List<Map<String, String>> information;
    private List<Relation> relations;    // targetUUID,targetName,content
    private String characterImage;
}
