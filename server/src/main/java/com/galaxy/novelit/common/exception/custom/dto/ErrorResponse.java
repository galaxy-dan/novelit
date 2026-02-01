package com.galaxy.novelit.common.exception.custom.dto;

import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private String code;
    private int status;
    private String message;

    public static ResponseEntity<ErrorResponse> of(ErrorCode e, String message) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus().value())
                        .code(e.name())
                        .message(message)
                        .build());
    }
}
