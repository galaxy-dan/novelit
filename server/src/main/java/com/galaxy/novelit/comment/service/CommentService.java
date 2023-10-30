package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentGetRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.dto.response.CommentGetResponseDto;

public interface CommentService {
    void addComment(CommentAddRequestDto commentAddRequestDto);

    CommentGetResponseDto getAllComments(String directoryUUID, Long spaceId);

    void updateComment(CommentUpdateRequestDto commentUpdateRequestDto);

    void deleteComment(CommentDeleteRequestDto commentDeleteRequestDto);
}
