package com.galaxy.novelit.notification.request;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest (@NotBlank String subscriberUUID){
}
