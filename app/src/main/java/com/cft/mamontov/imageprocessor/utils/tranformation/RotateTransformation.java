package com.cft.mamontov.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateTransformation implements Transformation {

    @Override public Bitmap transform(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}