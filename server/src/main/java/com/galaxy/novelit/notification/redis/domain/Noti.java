package com.galaxy.novelit.notification.redis.domain;

import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Noti {

    @Indexed
    private String subUUID;
    @Indexed
    private String directoryName;

    @Builder
    public Noti(String subUUID, String directoryName) {
        this.subUUID = subUUID;
        this.directoryName = directoryName;
    }

    public static Noti create(String subUUID, String directoryName){
        return Noti.builder()
            .subUUID(subUUID)
            .directoryName(directoryName)
            .build();
    }
}
