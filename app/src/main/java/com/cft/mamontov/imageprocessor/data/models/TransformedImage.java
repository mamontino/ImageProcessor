package com.cft.mamontov.imageprocessor.data.models;

import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.cft.mamontov.imageprocessor.utils.Processable;

public class TransformedImage implements Processable {

    private int mId;
    private String mPath;
    private Bitmap mBitmap = null;
    private ProgressBar mProgressBar = null;
    private int mProgress = 0;

    public TransformedImage(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    public void setProgress(int progress) {
        if (mProgressBar != null) {
            this.mProgress = progress;
            mProgressBar.setProgress(progress);
        }
    }

    @Override
    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
        progressBar.setProgress(mProgress);
    }
}
