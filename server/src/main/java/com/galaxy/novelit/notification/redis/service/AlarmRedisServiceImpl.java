package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmRedisServiceImpl implements AlarmRedisService{

    private final AlarmRedisRepository alarmRedisRepository;

    @Transactional
    public void save(AlarmRedisRequestDto alarmRedisRequestDto) {
        AlarmRedis responseDto = AlarmRedis.create(alarmRedisRequestDto);
        alarmRedisRepository.save(responseDto);
    }

    public List<AlarmGetResponseDto> getAllList(String subUUID) {
        List<AlarmRedis> all = alarmRedisRepository.findAllByNoti_SubUUID(subUUID)
            .orElseThrow(() -> new RuntimeException());

        return AlarmGetResponseDto.domainListToGetResDtoList(all);
    }
}
