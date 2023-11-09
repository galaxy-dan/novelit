package com.galaxy.novelit.notification.redis.dto.request;

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
public class AlarmRedisRequestDto {
    private String pubName;
    private String subUUID;
    private String directoryName;
}
