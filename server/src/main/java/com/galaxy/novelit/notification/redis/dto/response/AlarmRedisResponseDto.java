package com.galaxy.novelit.notification.redis.dto.response;

import com.galaxy.novelit.notification.redis.dto.request.AlarmRedisRequestDto;
import java.util.concurrent.TimeUnit;
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
public class AlarmRedisResponseDto{
    @Id
    String pubUUID;

    @TimeToLive(unit = TimeUnit.DAYS)
    String subUUID;
    @TimeToLive(unit = TimeUnit.DAYS)
    String notiUUID;
    public static AlarmRedisResponseDto create(AlarmRedisRequestDto alarmRedisRequestDto){
        return AlarmRedisResponseDto.builder()
            .pubUUID(alarmRedisRequestDto.getPubUUID())
            .subUUID(alarmRedisRequestDto.getSubUUID())
            .notiUUID(alarmRedisRequestDto.getNotiUUID())
            .build();
    }
}
