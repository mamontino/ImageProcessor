package com.cft.mamontov.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class InvertColorTransformation implements Transformation {

    @Override public Bitmap transform(Bitmap source) {
        Bitmap bmpMonochrome = source.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bmpMonochrome);
        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(0);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));
        canvas.drawBitmap(source, 0, 0, paint);
        return bmpMonochrome;
    }
}