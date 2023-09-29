package com.prizy.common.exception;

public final class RecordAlreadyExistsException extends ApiException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
