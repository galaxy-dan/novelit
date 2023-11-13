package com.galaxy.novelit.character.dto.res;

import com.galaxy.novelit.character.entity.RelationEntity.Relation;
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
public class CharacterDtoRes {
    private String groupUUID;
    private String characterUUID;
    private String characterName;
    private String description;
    private List<Map<String, String>> information;
    private List<Relation> relations;
    private boolean deleted;
    private String characterImage;
//    private Map<String, Double> characterNode;

}
