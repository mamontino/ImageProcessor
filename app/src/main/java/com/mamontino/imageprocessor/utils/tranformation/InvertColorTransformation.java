package com.mamontino.imageprocessor.utils.tranformation;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.squareup.picasso.Transformation;

public class InvertColorTransformation implements Transformation {

    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        final Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
        int a, r, g, b;
        int pixelColor;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelColor = bitmap.getPixel(x, y);
                a = Color.alpha(pixelColor);
                r = 255 - Color.red(pixelColor);
                g = 255 - Color.green(pixelColor);
                b = 255 - Color.blue(pixelColor);
                int color = Color.argb(a, r, g, b);
                bitmap.setPixel(x, y, color);
            }
        }
        return bitmap;
    }

    @Override public String key() {
        return "InvertColorTransformation()";
    }
}