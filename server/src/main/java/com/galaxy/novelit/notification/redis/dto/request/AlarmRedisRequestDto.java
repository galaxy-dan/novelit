package com.galaxy.novelit.notification.redis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmRedisRequestDto {
    private String pubUUID;
    private String subUUID;
    private String notiUUID;
}
