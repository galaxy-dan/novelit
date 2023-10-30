package com.galaxy.novelit.author.service;

import com.galaxy.novelit.author.dto.UserDto;
import java.util.List;

public interface UserService {
    String getUserNickname(String uuid);
    List<?> getUserWorkspace(String uuid);
}
