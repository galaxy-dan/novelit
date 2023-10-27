package com.galaxy.novelit.comment.dto.request;

import lombok.Data;

@Data
public class CommentDeleteRequestDto {
    private String directoryUUID;
    private Long spaceId;
    private String userUUID;
    private Long commentOrder;
    private String commentId;
    private String commentPassword;
}
