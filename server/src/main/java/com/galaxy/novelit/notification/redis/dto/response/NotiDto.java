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
    //@Indexed
    String subUUID;
    //@Indexed
    String notiUUID;
}
