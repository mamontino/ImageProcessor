package com.mamontino.imageprocessor.exceptions;

public class SourceNotFoundException extends IllegalArgumentException {
    public SourceNotFoundException(String message) {
        super(message);
    }
}
