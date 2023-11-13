package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
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
public class AlarmRedisServiceImpl implements AlarmRedisService{

    private final AlarmRedisRepository alarmRedisRepository;
/*
    private final RedisTemplate<String, AlarmRedis> redisTemplate;


    // redistemplate
    public void saveWithTTL(AlarmRedis alarmRedis) {
        redisTemplate.opsForValue().set(alarmRedis.getPubUUID(), alarmRedis, alarmRedis.getExpiration(), TimeUnit.DAYS);
    }

    public AlarmRedis get(String pubName){
        return redisTemplate.opsForValue().get(pubName);
    }
*/


    // crudRepository
    @Transactional
    public void save(AlarmRedisRequestDto alarmRedisRequestDto) {
        AlarmRedis responseDto = AlarmRedis.create(alarmRedisRequestDto);
        alarmRedisRepository.save(responseDto);
    }

    public List<AlarmGetResponseDto> getAllList(String subUUID) {
        List<AlarmGetResponseDto> list = new ArrayList<>();

        List<AlarmRedis> all = (List<AlarmRedis>) alarmRedisRepository.findAll();

        //log.info("d : {}" , all.get(0).getNoti().getSubUUID());

        for (AlarmRedis alarmRedis : all) {
            if (alarmRedis.getNoti().getSubUUID().equals(subUUID)) {
                list.add(AlarmGetResponseDto.domainToGetResDto(alarmRedis));
                //og.info(alarmRedis.getNoti().getSubUUID());
            }
        }

        return list;
    }
}
