package com.cft.mamontov.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class MirrorTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), m, false);
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return bitmap;
    }
}