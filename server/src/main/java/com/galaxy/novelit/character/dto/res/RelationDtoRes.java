package com.galaxy.novelit.character.dto.res;

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
@Getter
@Builder
public class RelationDtoRes {
    private String characterUUID;          // 캐릭터 UUID
    private String characterName;
    private List<Relation> relations;
}
