package com.galaxy.novelit.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentAddRequest(@NotBlank String spaceUUID,
                                @NotBlank String directoryUUID,
                                @NotBlank String commentContent,
                                @NotBlank String commentNickname) {

}
