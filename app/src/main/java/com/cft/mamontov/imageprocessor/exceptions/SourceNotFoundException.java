package com.cft.mamontov.imageprocessor.exceptions;

public class SourceNotFoundException extends IllegalArgumentException {

    public SourceNotFoundException() {
        super();
    }

    public SourceNotFoundException(String message) {
        super(message);
    }
}
