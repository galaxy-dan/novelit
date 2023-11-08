package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String userUUID;

    public static CommentInfo create(CommentAddRequestDto commentAddRequestDto, String userUUID) {
        UUID uuid = UUID.randomUUID();

        String str = uuid.toString();

        return CommentInfo.builder()
            .commentUUID(str)
            .commentContent(commentAddRequestDto.getCommentContent())
            .commentNickname(commentAddRequestDto.getCommentNickname())
            .userUUID(userUUID)
            .build();
    }

    public void updateCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
