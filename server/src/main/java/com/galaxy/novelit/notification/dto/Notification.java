package com.galaxy.novelit.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Notification {
    private Long notificationId; // 알림 고유 아이디
    private String notificationContent; // 내용
    private String notificationReceiver; // 받는사람
}
