package com.cft.mamontov.imageprocessor.presentation.exif;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.databinding.FragmentExifBinding;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.utils.ExifUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

public class ExifFragment extends DaggerDialogFragment {

    private FragmentExifBinding mBinding;

    @Inject
    public ExifFragment() {

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
    }
}

