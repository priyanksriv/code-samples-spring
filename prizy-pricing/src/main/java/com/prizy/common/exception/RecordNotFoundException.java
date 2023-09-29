package com.prizy.common.exception;

public final class RecordNotFoundException extends ApiException {

	private static final long serialVersionUID = -8928433347702090662L;

	public RecordNotFoundException(String msg){
		super(msg);
	}

	public RecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
