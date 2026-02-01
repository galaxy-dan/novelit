package com.galaxy.novelit.notification.redis.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AlarmRedisRequest(@NotBlank String pubName,
                                @NotBlank String subUUID,
                                @NotBlank String directoryName){
}
