package com.prizy.common.exception;

public final class BadRequestException extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
