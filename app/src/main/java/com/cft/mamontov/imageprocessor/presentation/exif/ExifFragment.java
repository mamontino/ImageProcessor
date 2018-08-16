package com.cft.mamontov.imageprocessor.presentation.exif;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cft.mamontov.imageprocessor.databinding.FragmentExifBinding;

import com.cft.mamontov.imageprocessor.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExifFragment extends DialogFragment {

    public static final String TAG = "ExifFragment";
    public static final String EXTRA_EXIF_URI = "EXTRA_EXIF_URI";

    private FragmentExifBinding mBinding;
    private String mPath = "";

    public static ExifFragment newInstance(String path) {
        ExifFragment fragment = new ExifFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_EXIF_URI, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        assert getArguments() != null;
        mPath = getArguments().getString(EXTRA_EXIF_URI);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exif, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        try {
            ExifInterface exif = new ExifInterface(mPath);
            showExif(exif);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading file: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showExif(ExifInterface exif) {
        String attributes = "";
        attributes += getTagString(ExifInterface.TAG_CAMARA_OWNER_NAME, exif);
        attributes += getTagString(ExifInterface.TAG_ARTIST, exif);
        attributes += getTagString(ExifInterface.TAG_IMAGE_DESCRIPTION, exif);
        attributes += getTagString(ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, exif);
        attributes += getTagString(ExifInterface.TAG_USER_COMMENT, exif);
        attributes += getTagString(ExifInterface.TAG_DATETIME, exif);
        attributes += getTagString(ExifInterface.TAG_FLASH, exif);
        attributes += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        attributes += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        attributes += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        attributes += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        attributes += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        attributes += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        attributes += getTagString(ExifInterface.TAG_MAKE, exif);
        attributes += getTagString(ExifInterface.TAG_MODEL, exif);
        attributes += getTagString(ExifInterface.TAG_ORIENTATION, exif);
        attributes += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);
        attributes += getTagString(ExifInterface.TAG_GAMMA, exif);
        mBinding.itemExifDesc.setText(attributes);
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }
}

