package com.seanlubbers.stacker.rest;

public class InvalidCpException extends RuntimeException {
    public InvalidCpException() {
        super();
    }

    public InvalidCpException(String message) {
        super(message);
    }

    public InvalidCpException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCpException(Throwable cause) {
        super(cause);
    }

    protected InvalidCpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
