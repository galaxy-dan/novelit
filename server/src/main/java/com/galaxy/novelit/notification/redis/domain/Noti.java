package com.galaxy.novelit.notification.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Noti {
    String subUUID;
    @Indexed
    String directoryName;

    public static Noti create(String subUUID, String directoryName){
        return Noti.builder()
            .subUUID(subUUID)
            .directoryName(directoryName)
            .build();
    }
}
