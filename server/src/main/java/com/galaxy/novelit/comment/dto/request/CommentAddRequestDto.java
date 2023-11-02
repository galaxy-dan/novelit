package com.galaxy.novelit.comment.dto.request;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    private String commentPassword;
}
