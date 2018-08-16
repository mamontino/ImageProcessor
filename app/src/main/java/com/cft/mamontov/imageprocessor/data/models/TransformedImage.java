package com.cft.mamontov.imageprocessor.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.cft.mamontov.imageprocessor.utils.Processable;

@Entity(tableName = "image")
public class TransformedImage implements Processable {

    public TransformedImage() {
    }

    @PrimaryKey
    private int id;
    private String path;

    @Ignore
    private Bitmap bitmap = null;
    @Ignore
    private ProgressBar progressBar = null;
    @Ignore
    private int progress = 0;

    @Ignore
    public TransformedImage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Ignore
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Ignore
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Ignore
    public int getProgress() {
        return progress;
    }

    @Ignore
    @Override
    public void setProgress(int progress) {
        if (progressBar != null) {
            this.progress = progress;
            progressBar.setProgress(progress);
        }
    }

    @Ignore
    @Override
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setProgress(progress);
    }
}
