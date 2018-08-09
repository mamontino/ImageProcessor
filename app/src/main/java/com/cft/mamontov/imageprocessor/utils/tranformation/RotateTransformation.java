package com.cft.mamontov.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateTransformation implements Transformation {

    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        matrix.postScale(1.5f, 1.5f);
        return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true);
    }
}