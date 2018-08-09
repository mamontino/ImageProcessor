package com.cft.mamontov.imageprocessor.source.models;

import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.cft.mamontov.imageprocessor.utils.Processable;


public class TransformedImage implements Processable {

    private Bitmap bitmap;
    private ProgressBar progressBar;

    public TransformedImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void setProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }

    @Override
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setProgress(0);
    }
}
