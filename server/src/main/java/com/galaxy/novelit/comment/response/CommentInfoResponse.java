package com.galaxy.novelit.comment.response;

import com.galaxy.novelit.comment.domain.CommentInfo;

import java.util.List;
import java.util.stream.Collectors;

public record CommentInfoResponse (String commentUUID,
                                   String commentContent,
                                   String commentNickname,
                                   String userUUID)
{
    public CommentInfoResponse (CommentInfo commentInfo) {
        this(commentInfo.getCommentUUID(), commentInfo.getCommentContent(),
                commentInfo.getCommentNickname(), commentInfo.getCommentUserUUID());
    }

    public static List<CommentInfoResponse> infoListToDtoList(List<CommentInfo> commentInfos) {
        return commentInfos.stream()
                .map(CommentInfoResponse::new)
                .collect(Collectors.toList());
    }
}
