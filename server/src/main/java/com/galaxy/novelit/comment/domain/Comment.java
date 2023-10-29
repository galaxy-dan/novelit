package com.galaxy.novelit.comment.domain;

import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private String _id;
    private Long spaceId;
    private String content;
    private String userUUID;
    private String directoryUUID;
    private List<CommentInfo> commentInfoList;
}
