package com.galaxy.novelit.alarm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SseEventName {
    ALARM_LIST("alertComment");

    private final String value;

    public static SseEventName getEnumFromValue(String value){
        for (SseEventName sseEventName : SseEventName.values()) {
            if (value.equals(sseEventName.getValue())) {
                return sseEventName;
            }
        }
        return null;
    }
}
