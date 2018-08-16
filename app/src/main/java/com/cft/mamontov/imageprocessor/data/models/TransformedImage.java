package com.cft.mamontov.imageprocessor.data.models;

import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.cft.mamontov.imageprocessor.utils.Processable;

public class TransformedImage implements Processable {

    private int id;
    private Bitmap bitmap = null;
    private ProgressBar progressBar = null;
    private int progress = 0;

    public TransformedImage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public void setProgress(int progress) {
        if (progressBar != null) {
            this.progress = progress;
            progressBar.setProgress(progress);
        }
    }

    @Override
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setProgress(progress);
    }
}
