package com.cft.mamontov.imageprocessor.exceptions;

public class NoNetworkException extends RuntimeException {

    public NoNetworkException() {
        super();
    }

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, Throwable t) {
        super(message, t);
    }
}
