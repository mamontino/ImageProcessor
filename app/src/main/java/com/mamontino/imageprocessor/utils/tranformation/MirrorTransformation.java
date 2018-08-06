package com.mamontino.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class MirrorTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(source, 0, 0, width, height, matrix, false);
    }

    @Override
    public String key() {
        return "MirrorTransformation()";
    }
}