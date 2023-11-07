package com.galaxy.novelit.notification.redis.dto.response;

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
public class AlarmRedisResponseDto{
    @Id
    private String pubUUID;

    private NotiDto notiDto;

    //@TimeToLive(unit = TimeUnit.DAYS)
    //private Long days;

    public static AlarmRedisResponseDto create(AlarmRedisRequestDto alarmRedisRequestDto){
        return AlarmRedisResponseDto.builder()
            .pubUUID(alarmRedisRequestDto.getPubUUID())
            .notiDto(NotiDto.create(alarmRedisRequestDto.getSubUUID(),
                alarmRedisRequestDto.getNotiUUID()))
            .build();
    }
}
