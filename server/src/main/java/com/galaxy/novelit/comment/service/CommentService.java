package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.repository.CommentInfoRepository;
import com.galaxy.novelit.comment.response.CommentInfoResponse;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.request.CommentDeleteRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import com.galaxy.novelit.comment.entity.Comment;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentInfoRepository commentInfoRepository;


    @Transactional
    public void addComment(CommentAddRequest commentAddRequest, String userUUID) {
        Optional<Comment> comment = commentRepository.findCommentBySpaceUUID(commentAddRequest.spaceUUID());

        if (comment.isPresent()) {
            putAnotherComment(comment.get(), commentAddRequest, userUUID);
        }
        else {
            createFirstComment(commentAddRequest, userUUID);
        }
    }

    private void createFirstComment(CommentAddRequest commentAddRequest, String userUUID) {
        Comment firstComment = Comment.createComment(commentAddRequest, userUUID);
        commentRepository.save(firstComment);
    }

    private void putAnotherComment(Comment comment, CommentAddRequest commentAddRequest, String userUUID) {
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        CommentInfo commentInfo = CommentInfo.createCommentInfo(commentAddRequest, userUUID);
        commentInfoList.add(commentInfo);

        comment.updateComment(commentInfoList);

        commentRepository.save(comment);
    }


    @Transactional(readOnly = true)
    public List<CommentInfoResponse> getAllCommentInfos(String spaceUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfos = comment.getCommentInfoList();

        return CommentInfoResponse.infoListToDtoList(commentInfos);
    }



    @Transactional
    public void updateComment(CommentUpdateRequest commentUpdateRequest, String userUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(commentUpdateRequest.spaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        comment.updateComment(commentUpdateRequest, userUUID);
    }


    @Transactional
    public void deleteComment(CommentDeleteRequest commentDeleteRequest, String userUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(commentDeleteRequest.spaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        comment.deleteComment(commentDeleteRequest, userUUID);
    }

    // 테스트용
    @Transactional(readOnly = true)
    public CommentInfo getCommentInfo(String spaceUUID, String commentUUID) {
        CommentInfo commentInfo = commentInfoRepository.findById(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 코멘트가 없어요."));

        return commentInfo;
        /*Comment comment = commentRepository.findCommentBySpaceUUID(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfos = comment.getCommentInfoList();

        for (CommentInfo commentInfo : commentInfos) {
            if (commentInfo.getCommentUUID().equals(commentUUID)) {
                return commentInfo;
            }
        }
        throw new CustomException(ErrorCode.NO_SUCH_COMMNET_INFO);*/
    }
}
