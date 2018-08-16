package com.cft.mamontov.imageprocessor.utils.events;

public class ProgressEvent {

    private final String message;
    private final long progress;
    private final boolean showing;
    private final boolean error;

    public ProgressEvent(String message, long progress, boolean showing, boolean error) {
        this.message = message;
        this.progress = progress;
        this.showing = showing;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public long getProgress() {
        return progress;
    }

    public boolean isShowing() {
        return showing;
    }

    public boolean isError() {
        return error;
    }
}
