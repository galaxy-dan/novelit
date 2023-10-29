package com.galaxy.novelit.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
    private Long commentOrder;
    private String commentContent;
    private String commentId;
    private String commentPassword;
}
