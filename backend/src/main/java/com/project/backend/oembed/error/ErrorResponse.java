package com.project.backend.oembed.error;

import com.project.backend.oembed.exception.MessageException;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(MessageException exceptionCode) {
        return new ErrorResponse(exceptionCode.getMessage());
    }

}
