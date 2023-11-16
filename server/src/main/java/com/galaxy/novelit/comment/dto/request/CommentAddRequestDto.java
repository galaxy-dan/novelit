package com.galaxy.novelit.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentAddRequestDto {
    private String spaceUUID;
    private String directoryUUID;
    private String commentContent;
    private String commentNickname;
}
