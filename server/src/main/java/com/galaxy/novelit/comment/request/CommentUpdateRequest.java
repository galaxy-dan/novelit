package com.galaxy.novelit.comment.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest (@NotBlank String spaceUUID,
                                    @NotBlank String commentUUID,
                                    @NotBlank String commentContent,
                                    @NotBlank String commentNickname) {
}
