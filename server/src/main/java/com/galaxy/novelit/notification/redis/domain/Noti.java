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

    private String pubName;
    @Indexed
    private String subUUID;
    @Indexed
    private String directoryName;

    public static Noti create(String pubName, String subUUID, String directoryName){
        return Noti.builder()
            .pubName(pubName)
            .subUUID(subUUID)
            .directoryName(directoryName)
            .build();
    }
}
