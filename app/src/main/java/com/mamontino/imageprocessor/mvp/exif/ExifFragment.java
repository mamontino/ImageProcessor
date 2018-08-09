package com.mamontino.imageprocessor.mvp.exif;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.di.scope.ActivityScoped;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

@ActivityScoped
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

