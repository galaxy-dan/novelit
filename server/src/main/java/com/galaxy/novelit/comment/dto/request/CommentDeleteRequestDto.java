package com.galaxy.novelit.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteRequestDto {
    private String spaceUUID;
    private String commentUUID;
    private String commentNickname;
}
