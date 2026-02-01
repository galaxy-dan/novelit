package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.common.utils.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfo extends BaseTimeEntity {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String commentUserUUID; // 코멘트 남긴사람의 uuid

    // 테스트코드용
    public CommentInfo(String commentUUID, String commentContent, String commentNickname, String commentUserUUID) {
        this.commentUUID = commentUUID;
        this.commentContent = commentContent;
        this.commentNickname = commentNickname;
        this.commentUserUUID = commentUserUUID;
    }

    @Builder
    public CommentInfo(String commentContent, String commentNickname, String commentUserUUID) {
        this.commentUUID = UUID.randomUUID().toString();
        this.commentContent = commentContent;
        this.commentNickname = commentNickname;
        this.commentUserUUID = commentUserUUID;
    }

    public static CommentInfo createCommentInfo(CommentAddRequest commentAddRequest, String commentUserUUID) {
        return CommentInfo.builder()
                .commentContent(commentAddRequest.commentContent())
                .commentNickname(commentAddRequest.commentNickname())
                .commentUserUUID(commentUserUUID)
                .build();
    }

    public void updateCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
