package com.galaxy.novelit.comment.dto.request;

import lombok.Data;

@Data
public class CommentDeleteRequestDto {
    private String directoryUUID;
    private String userUUID;
    private Long spaceId;
    private Long commentOrder;
    private String commentId;
    private String commentPassword;
}
