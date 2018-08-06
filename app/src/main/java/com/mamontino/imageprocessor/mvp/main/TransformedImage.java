package com.mamontino.imageprocessor.mvp.main;

import android.graphics.Bitmap;
import android.widget.ProgressBar;

public class TransformedImage {

    private int id;
    private Bitmap bitmap;
    private ProgressBar progressBar;

    public TransformedImage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setProgress(0);
    }
}
