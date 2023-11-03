package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String commentPassword;

    public static CommentInfo dtoToInfo(CommentInfoDto commentInfoDto) {
        UUID commentUUID = UUID.randomUUID();

        String strUUID = commentUUID.toString();

        return CommentInfo.builder()
            .commentUUID(strUUID)
            .commentContent(commentInfoDto.getCommentContent())
            .commentNickname(commentInfoDto.getCommentNickname())
            .commentPassword(commentInfoDto.getCommentPassword())
            .build();
    }

    public static CommentInfo create(CommentAddRequestDto commentAddRequestDto) {
        UUID uuid = UUID.randomUUID();

        String str = uuid.toString();

        return CommentInfo.builder()
            .commentUUID(str)
            .commentContent(commentAddRequestDto.getCommentContent())
            .commentNickname(commentAddRequestDto.getCommentNickname())
            .commentPassword(commentAddRequestDto.getCommentPassword())
            .build();
    }

    public void updateCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
