package com.galaxy.novelit.author.mapper;

import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
