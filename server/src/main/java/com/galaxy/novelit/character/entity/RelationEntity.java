package com.galaxy.novelit.character.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "relationship")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationEntity {
    @Id
    private String id;
    @Field(name = "character_uuid")
    private String characterUUID;
//    @Field(name = "character_name")
//    private String characterName;
    private List<Relation> relations;

    @Getter
    @Setter
    public static class Relation {
        private String targetUUID;
//        private String targetName;
        private String content;
    }
}
