package com.project.backend.oembed.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.backend.oembed.error.ErrorResponse;
import com.project.backend.oembed.exception.LogicException;

@RestControllerAdvice
public class GlobalExceptionResponse {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionResponse.class);

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(LogicException e) {

        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getMessage()));
    }

}
