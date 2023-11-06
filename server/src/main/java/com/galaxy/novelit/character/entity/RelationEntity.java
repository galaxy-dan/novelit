package com.galaxy.novelit.character.entity;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relationship")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationEntity {
    @Id
    private String id;
    private Map<String, String> strat;      // Map<UUID, 이름>
    private Map<String, String> end;        // Map<UUID, 이름>
    private String content;
}
