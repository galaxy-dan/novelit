package com.galaxy.novelit.author.service;

import com.galaxy.novelit.author.dto.UserDto;
import com.galaxy.novelit.author.mapper.UserMapper;
import com.galaxy.novelit.author.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Override
    public String getUserNickname(String uuid) {
        // user table에 가서 일치하는 닉네임 가져오기
        String nickname = userMapper.toDto(userRepository.findByUserUUID(uuid)).getNickname();
        return nickname;
    }

    @Override
    public List<?> getUserWorkspace(String uuid) {
        return null;
    }
}
