package com.galaxy.novelit.character.entity;

import jakarta.persistence.Id;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relation")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationEntity {
    @Id
    private String id;
    private Map<String, String> name;
    private Map<String, String> UUID;
    private Map<String, String> relation;
}
