package com.cft.mamontov.imageprocessor.utils;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.util.Log;

import com.cft.mamontov.imageprocessor.data.models.ExifInformation;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.support.media.ExifInterface.TAG_ARTIST;
import static android.support.media.ExifInterface.TAG_CAMARA_OWNER_NAME;
import static android.support.media.ExifInterface.TAG_DATETIME;
import static android.support.media.ExifInterface.TAG_EXPOSURE_MODE;
import static android.support.media.ExifInterface.TAG_EXPOSURE_TIME;
import static android.support.media.ExifInterface.TAG_FILE_SOURCE;
import static android.support.media.ExifInterface.TAG_IMAGE_DESCRIPTION;
import static android.support.media.ExifInterface.TAG_IMAGE_LENGTH;
import static android.support.media.ExifInterface.TAG_IMAGE_WIDTH;
import static android.support.media.ExifInterface.TAG_MODEL;
import static android.support.media.ExifInterface.TAG_ORIENTATION;
import static android.support.media.ExifInterface.TAG_WHITE_BALANCE;

public class ExifUtils {

    public static ExifInformation getExifInfo(ContentResolver contentResolver, Uri imageUri) {

        ExifInformation info = new ExifInformation();

        if (imageUri == null) return null;

        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(imageUri);
            if (inputStream == null) return null;

            ExifInterface exif = new ExifInterface(inputStream);
            String artist = exif.getAttribute(TAG_ARTIST);
            String orientation = exif.getAttribute(TAG_ORIENTATION);
            String owner = exif.getAttribute(TAG_CAMARA_OWNER_NAME);
            String date = exif.getAttribute(TAG_DATETIME);
            String whiteBalance = exif.getAttribute(TAG_WHITE_BALANCE);
            String model = exif.getAttribute(TAG_MODEL);
            String length = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String width = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String description = exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
            String source = exif.getAttribute(ExifInterface.TAG_FILE_SOURCE);
            String exposureTime = exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
            String exposureMode = exif.getAttribute(ExifInterface.TAG_EXPOSURE_MODE);

            info.setArtist(artist);
            info.setOrientation(orientation);
            info.setOwner(owner);
            info.setDate(date);
            info.setWhiteBalance(whiteBalance);
            info.setModel(model);
            info.setLength(length);
            info.setWidth(width);
            info.setDescription(description);
            info.setSource(source);
            info.setExposureMode(exposureMode);
            info.setExposureTime(exposureTime);

            return info;

        } catch (IOException e) {
            Log.e("Error getting Exif data", e.getMessage());
            return null;
        } finally {
            closeSilently(inputStream);
        }
    }

    public static boolean copyExifInfo(File sourceFile, File destFile) {
        if (sourceFile == null || destFile == null) return false;
        try {
            ExifInterface exifSource = new ExifInterface(sourceFile.getAbsolutePath());
            ExifInterface exifDest = new ExifInterface(destFile.getAbsolutePath());

            if (exifSource.getAttribute(TAG_ORIENTATION) != null) {
                exifDest.setAttribute(TAG_ORIENTATION, exifSource.getAttribute(TAG_ORIENTATION));
            }
            if (exifSource.getAttribute(TAG_ARTIST) != null) {
                exifDest.setAttribute(TAG_ARTIST, exifSource.getAttribute(TAG_ARTIST));
            }
            if (exifSource.getAttribute(TAG_CAMARA_OWNER_NAME) != null) {
                exifDest.setAttribute(TAG_CAMARA_OWNER_NAME, exifSource.getAttribute(TAG_CAMARA_OWNER_NAME));
            }
            if (exifSource.getAttribute(TAG_DATETIME) != null) {
                exifDest.setAttribute(TAG_DATETIME, exifSource.getAttribute(TAG_DATETIME));
            }
            if (exifSource.getAttribute(TAG_WHITE_BALANCE) != null) {
                exifDest.setAttribute(TAG_WHITE_BALANCE, exifSource.getAttribute(TAG_WHITE_BALANCE));
            }
            if (exifSource.getAttribute(TAG_MODEL) != null) {
                exifDest.setAttribute(TAG_MODEL, exifSource.getAttribute(TAG_MODEL));
            }
            if (exifSource.getAttribute(TAG_IMAGE_LENGTH) != null) {
                exifDest.setAttribute(TAG_IMAGE_LENGTH, exifSource.getAttribute(TAG_IMAGE_LENGTH));
            }
            if (exifSource.getAttribute(TAG_IMAGE_WIDTH) != null) {
                exifDest.setAttribute(TAG_IMAGE_WIDTH, exifSource.getAttribute(TAG_IMAGE_WIDTH));
            }
            if (exifSource.getAttribute(TAG_IMAGE_DESCRIPTION) != null) {
                exifDest.setAttribute(TAG_IMAGE_DESCRIPTION, exifSource.getAttribute(TAG_IMAGE_DESCRIPTION));
            }
            if (exifSource.getAttribute(TAG_FILE_SOURCE) != null) {
                exifDest.setAttribute(TAG_FILE_SOURCE, exifSource.getAttribute(TAG_FILE_SOURCE));
            }
            if (exifSource.getAttribute(TAG_EXPOSURE_MODE) != null) {
                exifDest.setAttribute(TAG_EXPOSURE_MODE, exifSource.getAttribute(TAG_EXPOSURE_MODE));
            }
            if (exifSource.getAttribute(TAG_EXPOSURE_TIME) != null) {
                exifDest.setAttribute(TAG_EXPOSURE_TIME, exifSource.getAttribute(TAG_EXPOSURE_TIME));
            }

            exifDest.saveAttributes();
            return true;
        } catch (IOException e) {
            Log.e("Error copying Exif data", e.getMessage());
            return false;
        }
    }

    public static boolean setExifArtist(File sourceFile) {
        if (sourceFile == null) return false;
        try {
            ExifInterface exifDest = new ExifInterface(sourceFile.getAbsolutePath());
            exifDest.setAttribute(TAG_ARTIST, AppConstants.APP_NAME);
            exifDest.setAttribute(TAG_CAMARA_OWNER_NAME, AppConstants.APP_NAME);
            exifDest.saveAttributes();
            return true;
        } catch (IOException e) {
            Log.e("Error saving Exif data", e.getMessage());
            return false;
        }
    }

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            Log.e("Error closing inputStream", t.getMessage());
        }
    }
}
