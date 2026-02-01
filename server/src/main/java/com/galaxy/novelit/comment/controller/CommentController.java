package com.galaxy.novelit.comment.controller;

import com.galaxy.novelit.comment.response.CommentInfoResponse;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.request.CommentDeleteRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import com.galaxy.novelit.comment.service.CommentService;
import com.galaxy.novelit.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentAddRequest commentAddRequest,
                                           Authentication authentication){ // uuid : 로그인한 사람 uuid

        String publisherUUID = authentication.getName();

        commentService.addComment(commentAddRequest, publisherUUID);

        notificationService.notifyAllCommentReceiver(commentAddRequest, publisherUUID);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentInfoResponse>> getAllCommentInfos(@RequestParam("spaceUUID") String spaceUUID) {
        List<CommentInfoResponse> commentInfoResponses = commentService.getAllCommentInfos(spaceUUID);
        return ResponseEntity.ok(commentInfoResponses);
    }

    @PatchMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateRequest commentUpdateRequest
    , Authentication authentication) {
        commentService.updateComment(commentUpdateRequest, authentication.getName());
        // 수정알림 보내야할듯.
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest
    , Authentication authentication) {
        commentService.deleteComment(commentDeleteRequest, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
