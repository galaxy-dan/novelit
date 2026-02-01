package com.galaxy.novelit.comment.request;

import jakarta.validation.constraints.NotBlank;

public record CommentDeleteRequest (@NotBlank String spaceUUID,
                                    @NotBlank String commentUUID,
                                    @NotBlank String commentNickname) {

}
