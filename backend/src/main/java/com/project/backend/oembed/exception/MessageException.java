package com.project.backend.oembed.exception;

import lombok.Getter;

public enum MessageException {
    URL_NOT_VALID("URL 주소가 유효하지 않습니다."),
    HOST_NOT_VALID("HOST 주소가 유효하지 않습니다."),
    INSTAGRAM_NOT_VALID("인스타그램은 서비스 사용에 제한이 있습니다."),
    FAILED_CREATE_JSON_DATA("JSON DATA 생성에 실패하였습니다.");

    @Getter
    private String message;

    MessageException(String message) {
        this.message = message;
    }

}
