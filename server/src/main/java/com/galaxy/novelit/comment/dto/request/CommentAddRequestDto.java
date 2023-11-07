package com.galaxy.novelit.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentAddRequestDto {
    private String spaceUUID;
    private String directoryUUID;
    private String commentContent;
    private String commentNickname;
}
