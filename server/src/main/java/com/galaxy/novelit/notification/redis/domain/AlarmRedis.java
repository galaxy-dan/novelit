package com.galaxy.novelit.notification.redis.domain;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("AlarmRedis")
public class AlarmRedis {
    @Id
    private String pubName;
    private Noti noti;

    @TimeToLive // 초단위
    private Long expiration;

    public static AlarmRedis create(AlarmRedisRequestDto alarmRedisRequestDto){
        return AlarmRedis.builder()
            .pubName(alarmRedisRequestDto.getPubName())
            .noti(Noti.create(alarmRedisRequestDto.getSubUUID()
                , alarmRedisRequestDto.getDirectoryName()))
            .expiration(1L * 60 * 24) // 유효기간 1일
            .build();
    }
}
