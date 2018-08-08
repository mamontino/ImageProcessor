package com.mamontino.imageprocessor.utils;

import android.widget.ProgressBar;

public interface Processable {
    boolean inProgress();
    void setProgress(int progress, boolean run);
    void setProgressBar(ProgressBar progressBar);
}
