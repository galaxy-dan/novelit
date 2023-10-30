package com.galaxy.novelit.comment.controller;

import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentGetRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.dto.response.CommentGetResponseDto;
import com.galaxy.novelit.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentAddRequestDto commentAddRequestDto){
        commentService.addComment(commentAddRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CommentGetResponseDto> getAllComments(@RequestParam("directoryUUID") String directoryUUID, @RequestParam("spaceId") Long spaceId) {
        return ResponseEntity.ok(commentService.getAllComments(directoryUUID, spaceId));
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateComment(commentUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDeleteRequestDto commentDeleteRequestDto) {
        commentService.deleteComment(commentDeleteRequestDto);
        return ResponseEntity.ok().build();
    }
}
