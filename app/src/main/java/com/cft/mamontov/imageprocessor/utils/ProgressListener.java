package com.cft.mamontov.imageprocessor.utils;

public interface ProgressListener {

    void update(long bytesRead, long contentLength, boolean done);
}
