package com.galaxy.novelit.notification.redis.entity;

import com.galaxy.novelit.common.utils.BaseTimeEntity;
import com.galaxy.novelit.notification.redis.domain.Noti;
import com.galaxy.novelit.notification.redis.request.AlarmRedisRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("AlarmRedis")
public class AlarmRedis{
    @Id
    private String pubName;
    private Noti noti;

    @TimeToLive // 초단위
    private Long expiration;

    @Builder
    public AlarmRedis(String pubName, Noti noti, Long expiration) {
        this.pubName = pubName;
        this.noti = noti;
        this.expiration = expiration;
    }

    public static AlarmRedis create(AlarmRedisRequest alarmRedisRequest){
        return AlarmRedis.builder()
            .pubName(alarmRedisRequest.pubName())
            .noti(Noti.create(alarmRedisRequest.subUUID()
                , alarmRedisRequest.directoryName()))
            .expiration(1L * 60 * 24) // 유효기간 1일
            .build();
    }
}
