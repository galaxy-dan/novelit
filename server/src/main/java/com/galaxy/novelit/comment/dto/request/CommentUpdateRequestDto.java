package com.galaxy.novelit.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequestDto {
    private String spaceUUID;
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String commentPassword;
}
