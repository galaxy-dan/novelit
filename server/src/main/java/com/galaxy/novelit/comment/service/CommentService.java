package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import java.util.List;

public interface CommentService {
    void addComment(CommentAddRequestDto commentAddRequestDto, String userUUID);

    List<CommentInfoDto> getAllComments(String spaceUUID);

    void updateComment(CommentUpdateRequestDto commentUpdateRequestDto, String userUUID);

    void deleteComment(CommentDeleteRequestDto commentDeleteRequestDto, String userUUID);
}
