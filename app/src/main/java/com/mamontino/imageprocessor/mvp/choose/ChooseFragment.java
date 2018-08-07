package com.mamontino.imageprocessor.mvp.choose;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.FragmentChooseBinding;
import com.mamontino.imageprocessor.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

@ActivityScoped
public class ChooseFragment extends DaggerDialogFragment {

    public static final int REQUEST_CODE_LOCAL = 1;
    public static final int REQUEST_CODE_CAMERA = 2;

    private OnSourceListener mOnSourceListener;
    private FragmentChooseBinding mBinding;

    @Inject
    public ChooseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.chooseFragmentBtnCamera.setOnClickListener(v -> {
            mOnSourceListener.onSourceSelected(REQUEST_CODE_CAMERA);
            this.dismiss();
        });

        mBinding.chooseFragmentBtnLocal.setOnClickListener(v -> {
            mOnSourceListener.onSourceSelected(REQUEST_CODE_LOCAL);
            this.dismiss();
        });
    }

    public interface OnSourceListener {
        void onSourceSelected(int source);
    }
}

