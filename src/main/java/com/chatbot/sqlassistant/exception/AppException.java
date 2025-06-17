package com.chatbot.sqlassistant.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;
	private final Integer errorCode;

    public AppException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}