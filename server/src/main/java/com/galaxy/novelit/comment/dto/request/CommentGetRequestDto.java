package com.galaxy.novelit.comment.dto.request;

import lombok.Data;

@Data
public class CommentGetRequestDto {
    private String directoryUUID;
    private Long spaceId;
}
