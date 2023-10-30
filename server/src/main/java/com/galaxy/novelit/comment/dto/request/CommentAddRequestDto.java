package com.galaxy.novelit.comment.dto.request;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentAddRequestDto {
    private Long spaceId;
    private String content;
    private String userUUID;
    private String directoryUUID;
    private List<CommentInfoDto> commentList;
}
