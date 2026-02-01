package com.galaxy.novelit.notification.redis.service;

import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.notification.redis.entity.AlarmRedis;
import com.galaxy.novelit.notification.redis.request.AlarmRedisRequest;
import com.galaxy.novelit.notification.redis.response.AlarmGetResponse;
import com.galaxy.novelit.notification.redis.repository.AlarmRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmRedisService {

    private final AlarmRedisRepository alarmRedisRepository;
    private final UserRepository userRepository;
    private final DirectoryRepository directoryRepository;

    @Transactional
    public void save(AlarmRedisRequest alarmRedisRequest) {
        AlarmRedis alarmRedis = AlarmRedis.create(alarmRedisRequest);
        alarmRedisRepository.save(alarmRedis);
    }

    @Transactional(readOnly = true)
    public List<AlarmGetResponse> getAllList(String subUUID) {

        List<AlarmRedis> alarms = alarmRedisRepository.findAllByNoti_SubUUID(subUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "등록된 알람리스트가 없습니다."));

        User user = userRepository.findByUserUUID(subUUID);

        String userNickname;

        if(Objects.isNull(user)){ // 편집자
            userNickname = "";
        }
        else{ // 작가
            userNickname = userRepository.findByUserUUID(subUUID).getNickname();
        }

        List<AlarmRedis> alarmRedisList;
        
        if (userNickname.equals("")) { // 편집자
            Directory directory = directoryRepository.findDirectoryByUuid(subUUID)
                    .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_DIRECTORY));

            String authorUUID = directory.getUserUUID();

            String authorNickname = userRepository.findByUserUUID(authorUUID).getNickname();

            alarmRedisList = alarms.stream()
                .filter(alarm -> alarm.getPubName().equals(authorNickname)) // 작가 닉네임 알람만 조회
                .collect(Collectors.toList());
        }
        else{ // 작가
            alarmRedisList = alarms.stream()
                .filter(alarm -> !alarm.getPubName().equals(userNickname)) // 자신과 같은 이름 알람리스트 제외
                .collect(Collectors.toList());
        }

        return AlarmGetResponse.domainListToGetResDtoList(alarmRedisList);
    }
}
