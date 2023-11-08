package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmRedisResponseDto;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;
import java.util.ArrayList;
import java.util.List;
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

    public List<AlarmRedisResponseDto> getAllList(String subUUID) {
        List<AlarmRedisResponseDto> list = new ArrayList<>();
        List<AlarmRedisResponseDto> all = (List<AlarmRedisResponseDto>) alarmRedisRepository.findAll();
        for (AlarmRedisResponseDto dto : all) {
            if (dto.getNotiDto().getSubUUID().equals(subUUID)) {
                list.add(dto);
            }
        }

        // pubUUID -> getNickname

        return all;

       /* for (AlarmRedisResponseDto dto:
        alarmRedisRepository.findByNotiDto_SubUUID(subUUID)) {
            log.info(dto.getPubUUID());
        }

        List<AlarmRedisResponseDto> list = alarmRedisRepository.findByNotiDto_SubUUID(subUUID);

        log.info(list.get(0).getPubUUID());

        return list;*/
    }
}
