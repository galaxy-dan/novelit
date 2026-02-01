package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.entity.Comment;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.comment.request.CommentDeleteRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    String spaceUUID;
    Comment comment;
    List<CommentInfo> commentInfos;
    CommentInfo commentInfo;

    String authorUUID;
    String userUUID;
    String commentUUID;

    @BeforeEach
    void setup(){
        spaceUUID = "space-asdf";
        authorUUID = "kim-asdf";
        userUUID = "ryu-asdf";

        commentUUID = "commentinfo-asdf";

        commentInfo = new CommentInfo(commentUUID, "밥먹고 싶어요우", "류현진",
                userUUID);

        commentInfos = new ArrayList<>();
        commentInfos.add(commentInfo);

        comment = new Comment("_id", spaceUUID,
                "directory-asdf", commentInfos);
    }

    @Test
    @DisplayName("댓글 수정하는 겸 MongoDB 영속성 테스트")
    void updateComment(){
        given(commentRepository.findCommentBySpaceUUID(any())).willReturn(Optional.ofNullable(comment));

        CommentUpdateRequest request = new CommentUpdateRequest(spaceUUID,
                commentUUID, "밥드세요우", "Ryu");

        commentService.updateComment(request, userUUID);

        String updateContent = commentService.getCommentInfo(spaceUUID, commentUUID).getCommentContent();
        String expectedContent = "밥드세요우";

        Assertions.assertThat(expectedContent).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception{
        //given
        given(commentRepository.findCommentBySpaceUUID(any())).willReturn(Optional.ofNullable(comment));

        CommentDeleteRequest request = new CommentDeleteRequest(spaceUUID, commentUUID, "류현진");

        commentService.deleteComment(request, userUUID);

        Assertions.assertThat(commentRepository.findCommentBySpaceUUID(spaceUUID).get().getCommentInfoList().size())
                .isEqualTo(0);
    }
}
