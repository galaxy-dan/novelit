package com.galaxy.novelit.notification.redis.dto.response;

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
public class NotiDto {
    String subUUID;
    String notiUUID;

    public static NotiDto create(String subUUID, String notiUUID){
        return NotiDto.builder()
            .subUUID(subUUID)
            .notiUUID(notiUUID)
            .build();
    }
}
