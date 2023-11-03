package com.galaxy.novelit.notification.dto.response;

import java.util.UUID;
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
public class NotificationResponseDto {
    private String subscriberUUID; // 편집자 UUID
    private String notificationUUID;
    private String notificationContent;
    private String publisherId;

    /*public static NotificationResponseDto createAlarmComment(NotificationRequestDto notificationRequestDto){
        UUID uuid = UUID.randomUUID();

        String notiUUID = uuid.toString();

        return NotificationResponseDto.builder()
            .subscriberUUID(notificationRequestDto.getSubscriberUUID())
            .notificationUUID(notiUUID)
            .notificationContent(notificationRequestDto.getSubscriberUUID() + " 님이 댓글을 남겼습니다.")
            .build();
    }*/

    public static NotificationResponseDto createAlarmComment(String commentNickname, String subscriberUUID){
        UUID uuid = UUID.randomUUID();

        String notiUUID = uuid.toString();

        return NotificationResponseDto.builder()
            .subscriberUUID(subscriberUUID)
            .notificationUUID(notiUUID)
            .notificationContent(commentNickname + " 님이 댓글을 남겼습니다.")
            .build();
    }
}
