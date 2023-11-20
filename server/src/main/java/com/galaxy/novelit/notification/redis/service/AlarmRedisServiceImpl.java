package com.galaxy.novelit.notification.redis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import com.galaxy.novelit.notification.redis.dto.response.AlarmGetResponseDto;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmRedisServiceImpl implements AlarmRedisService{

    private final AlarmRedisRepository alarmRedisRepository;
    private final UserRepository userRepository;
    private final DirectoryRepository directoryRepository;

    @Transactional
    public void save(AlarmRedisRequestDto alarmRedisRequestDto) {
        AlarmRedis responseDto = AlarmRedis.create(alarmRedisRequestDto);
        alarmRedisRepository.save(responseDto);
    }

    public List<AlarmGetResponseDto> getAllList(String subUUID) {

        List<AlarmRedis> allList = alarmRedisRepository.findAllByNoti_SubUUID(subUUID).get();

        User user = userRepository.findByUserUUID(subUUID);
        String userNickname;
        if(user == null){ // 편집자
            userNickname = "";
        }else{ // 작가
            userNickname = userRepository.findByUserUUID(subUUID).getNickname();
        }

        List<AlarmRedis> alarmRedisList;
        
        if (userNickname.equals("")) { // 편집자
            String authorUUID = directoryRepository.findDirectoryByUuid(subUUID).get().getUserUUID();

            String authorNickname = userRepository.findByUserUUID(authorUUID).getNickname();

            alarmRedisList = allList.stream()
                .filter(alarm -> alarm.getPubName().equals(authorNickname)) // 작가 닉네임 알람만 조회
                .collect(Collectors.toList());
        }
        else{ // 작가
            alarmRedisList = allList.stream()
                .filter(alarm -> !alarm.getPubName().equals(userNickname)) // 자신과 같은 이름 알람리스트 제외
                .collect(Collectors.toList());
        }
        return AlarmGetResponseDto.domainListToGetResDtoList(alarmRedisList);
    }
}
