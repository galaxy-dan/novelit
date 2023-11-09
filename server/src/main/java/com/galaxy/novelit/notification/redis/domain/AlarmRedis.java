package com.galaxy.novelit.notification.redis.domain;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("AlarmRedis")
public class AlarmRedis {
    @Id
    private String pubName; // 보내는 사람

    private Noti noti;

    //@TimeToLive(unit = TimeUnit.DAYS)
    //private Long days;

    public static AlarmRedis create(AlarmRedisRequestDto alarmRedisRequestDto){
        return AlarmRedis.builder()
            .pubName(alarmRedisRequestDto.getPubName())
            .noti(Noti.create(alarmRedisRequestDto.getSubUUID(), alarmRedisRequestDto.getDirectoryName()))
            .build();
    }
}
