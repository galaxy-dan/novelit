package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmRedisServiceImpl implements AlarmRedisService{

    private final AlarmRedisRepository alarmRedisRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(AlarmRedisRequestDto alarmRedisRequestDto) {
        AlarmRedis responseDto = AlarmRedis.create(alarmRedisRequestDto);
        alarmRedisRepository.save(responseDto);
    }

    public List<AlarmGetResponseDto> getAllList(String subUUID) {
        List<AlarmRedis> allList = alarmRedisRepository.findAllByNoti_SubUUID(subUUID).get();

        String userNickname = userRepository.findByUserUUID(subUUID).getNickname();

        List<AlarmRedis> alarmRedisList = allList.stream()
            .filter(alarm -> !alarm.getPubName().equals(userNickname))
            .collect(Collectors.toList());


        return AlarmGetResponseDto.domainListToGetResDtoList(alarmRedisList);
    }
}
