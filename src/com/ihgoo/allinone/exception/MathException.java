package com.ihgoo.allinone.exception;

public class MathException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public MathException() {
    }

    public MathException(String detailMessage) {
        super(detailMessage);
    }

    public MathException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MathException(Throwable throwable) {
        super(throwable);
    }
	
}
