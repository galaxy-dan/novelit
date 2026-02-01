package com.galaxy.novelit.notification.redis.response;

import com.galaxy.novelit.notification.redis.entity.AlarmRedis;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


@Builder
public record AlarmGetResponse (String pubName,
                                String directoryName){

    public static AlarmGetResponse domainToGetResDto(AlarmRedis alarmRedis) {
        return AlarmGetResponse.builder()
            .pubName(alarmRedis.getPubName())
            .directoryName(alarmRedis.getNoti().getDirectoryName())
            .build();
    }

    public static List<AlarmGetResponse> domainListToGetResDtoList(List<AlarmRedis> alarmRedisList) {
        return alarmRedisList.stream()
                .map(AlarmGetResponse::domainToGetResDto)
                .collect(Collectors.toList());
    }
}
