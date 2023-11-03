package com.galaxy.novelit.author.dto;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@SuperBuilder(toBuilder = true)
public class UserDto{
    private long userId;
    private String userUUID;
    private String email;
    private String nickname;
}
