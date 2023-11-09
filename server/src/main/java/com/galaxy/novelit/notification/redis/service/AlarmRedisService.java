package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
import java.util.List;

public interface AlarmRedisService {

    void save(AlarmRedisRequestDto alarmRedisRequestDto);
    List<AlarmGetResponseDto> getAllList(String subUUID);
}
