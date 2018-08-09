package com.cft.mamontov.imageprocessor.utils.load;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;

public interface Loadable {
    Bitmap createBitmap(Intent data) throws IOException;
}
