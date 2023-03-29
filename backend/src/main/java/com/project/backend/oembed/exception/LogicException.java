package com.project.backend.oembed.exception;

import lombok.Getter;

@Getter
public class LogicException extends RuntimeException {
    private MessageException exceptionCode;

    public LogicException(MessageException exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
