package com.cft.mamontov.imageprocessor.utils.events;

public interface ProgressListener {
    void update(String downloadIdentifier, long bytesRead, long contentLength, boolean done);
}