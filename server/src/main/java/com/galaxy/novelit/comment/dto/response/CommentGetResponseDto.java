package com.galaxy.novelit.comment.dto.response;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentGetResponseDto {
    private String directoryUUID;
    private Long spaceId;
    private List<CommentInfoDto> commentInfoDtoList;
}
