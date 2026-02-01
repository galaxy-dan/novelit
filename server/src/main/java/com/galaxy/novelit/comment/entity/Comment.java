package com.galaxy.novelit.comment.entity;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.request.CommentDeleteRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    private String _id;
    private String spaceUUID;
    private String directoryUUID;
    private List<CommentInfo> commentInfoList;

    // 테스트코드용
    public Comment(String _id, String spaceUUID, String directoryUUID, List<CommentInfo> commentInfoList) {
        this._id = _id;
        this.spaceUUID = spaceUUID;
        this.directoryUUID = directoryUUID;
        this.commentInfoList = commentInfoList;
    }

    @Builder
    public Comment(String spaceUUID, String directoryUUID, List<CommentInfo> commentInfoList) {
        this.spaceUUID = spaceUUID;
        this.directoryUUID = directoryUUID;
        this.commentInfoList = commentInfoList;
    }

    public static Comment createComment(CommentAddRequest commentAddRequest, String userUUID) {
        List<CommentInfo> commentInfoList = new ArrayList<>();

        CommentInfo commentInfo = CommentInfo.builder()
                .commentContent(commentAddRequest.commentContent())
                .commentNickname(commentAddRequest.commentNickname())
                .commentUserUUID(userUUID)
                .build();


        commentInfoList.add(commentInfo);


        return Comment.builder()
            .spaceUUID(commentAddRequest.spaceUUID())
            .directoryUUID(commentAddRequest.directoryUUID())
            .commentInfoList(commentInfoList)
            .build();
    }

    public void updateComment(List<CommentInfo> list){
        this.commentInfoList = list;
    }

    public void updateComment(CommentUpdateRequest commentUpdateRequest, String userUUID) {
        validateCommentInfoList(this.commentInfoList);

        for (CommentInfo commentInfo : commentInfoList) {
            if (validateSameCommentWriter(commentInfo, commentUpdateRequest, userUUID)) {
                commentInfo.updateCommentContent(commentUpdateRequest.commentContent());
                return;
            }
        }

        throw new CustomException(ErrorCode.BAD_REQUEST, "글쓴이가 아니거나 수정하려는 댓글이 없습니다.");
    }
    private boolean validateSameCommentWriter(CommentInfo commentInfo, CommentUpdateRequest commentUpdateRequest,
                                              String loginUUID) {
        return commentInfo.getCommentUUID().equals(commentUpdateRequest.commentUUID())
                && commentInfo.getCommentUserUUID().equals(loginUUID);
    }

    private void validateCommentInfoList(List<CommentInfo> commentInfoList) {
        if (Objects.isNull(commentInfoList) || commentInfoList.size() == 0){
            throw new CustomException(ErrorCode.NOT_FOUND, "댓글목록이 없습니다.");
        }
    }

    public void deleteComment(CommentDeleteRequest commentDeleteRequest, String userUUID) {
        validateCommentInfoList(this.commentInfoList);

        for (CommentInfo commentInfo :commentInfoList) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (validateSameCommentWriter(commentInfo, commentDeleteRequest, userUUID)) {
                commentInfoList.remove(commentInfo);
                updateComment(this.commentInfoList);
                return;
            }
        }
        throw new CustomException(ErrorCode.BAD_REQUEST, "글쓴이가 아니거나 삭제하려는 댓글이 없습니다.");
    }

    private boolean validateSameCommentWriter(CommentInfo commentInfo,
                                              CommentDeleteRequest commentDeleteRequest,
                                              String userUUID) {
        return commentInfo.getCommentUUID().equals(commentDeleteRequest.commentUUID())
                && commentInfo.getCommentUserUUID().equals(userUUID);
    }
}
