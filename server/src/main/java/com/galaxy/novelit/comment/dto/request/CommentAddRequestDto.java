package com.galaxy.novelit.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentAddRequestDto {
    private String spaceUUID;
    private String directoryUUID;
    private String commentContent;
    private String commentNickname;

    public void setSpaceUUID(String spaceUUID) {
        this.spaceUUID = (spaceUUID != null) ? spaceUUID : "";
    }
}
