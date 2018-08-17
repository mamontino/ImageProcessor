package com.cft.mamontov.imageprocessor.utils.events;

public class ProgressEvent {

    private final String mMessage;
    private final long mProgress;
    private final boolean showing;
    private final boolean error;

    public ProgressEvent(String message, long progress, boolean showing, boolean error) {
        this.mMessage = message;
        this.mProgress = progress;
        this.showing = showing;
        this.error = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public long getProgress() {
        return mProgress;
    }

    public boolean isShowing() {
        return showing;
    }

    public boolean isError() {
        return error;
    }
}
