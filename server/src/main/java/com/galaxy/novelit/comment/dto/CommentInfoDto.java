package com.galaxy.novelit.comment.dto;

import lombok.Data;

@Data
public class CommentInfoDto {
    private Long commentOrder;
    private String commentContent;
    private String commentId;
    private String commentPassword;
}
