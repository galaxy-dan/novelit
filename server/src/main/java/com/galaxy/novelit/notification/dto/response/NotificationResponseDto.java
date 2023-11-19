package com.galaxy.novelit.notification.dto.response;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private String type;
    private String content;


    public static NotificationResponseDto createAlarmComment(String commentNickname){
        return NotificationResponseDto.builder()
            .type("alertComment")
            .content(commentNickname + " 님이 댓글을 남겼습니다.")
            .build();
    }

    public static NotificationResponseDto create(CommentAddRequestDto commentAddRequestDto) {
        return NotificationResponseDto.builder()
            .type("alertComment")
            .content(commentAddRequestDto.getCommentNickname() + " 님이 댓글을 남겼습니다.")
            .build();
    }
}
