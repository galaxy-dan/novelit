package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmRedisResponseDto;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmRedisService {

    private final AlarmRedisRepository alarmRedisRepository;

    @Transactional
    public void save(AlarmRedisRequestDto alarmRedisRequestDto) {
        AlarmRedisResponseDto responseDto = AlarmRedisResponseDto.create(alarmRedisRequestDto);
        alarmRedisRepository.save(responseDto);
    }

}
