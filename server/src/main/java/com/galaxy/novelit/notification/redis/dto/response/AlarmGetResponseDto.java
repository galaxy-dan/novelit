package com.galaxy.novelit.notification.redis.dto.response;

import com.galaxy.novelit.notification.redis.domain.AlarmRedis;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmGetResponseDto {
    private String pubName;
    private String directoryName;

    public static AlarmGetResponseDto domainToGetResDto(AlarmRedis alarmRedis) {
        return AlarmGetResponseDto.
            builder()
            .pubName(alarmRedis.getNoti().getPubName())
            .directoryName(alarmRedis.getNoti().getDirectoryName())
            .build();
    }

    public static List<AlarmGetResponseDto> domainListToGetResDtoList(
        List<AlarmRedis> alarmRedisList) {
        List<AlarmGetResponseDto> list = new ArrayList<>();

        for (AlarmRedis alarmRedis : alarmRedisList) {
            list.add(domainToGetResDto(alarmRedis));
        }

        return list;
    }
}
