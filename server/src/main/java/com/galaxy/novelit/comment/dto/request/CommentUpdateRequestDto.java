package com.galaxy.novelit.comment.dto.request;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentUpdateRequestDto {
    private String directoryUUID;
    private Long spaceId;

    private Long commentOrder;

    private String updateContent;

    private String userUUID;

    private String commentId;
    private String commentPassword;
}
