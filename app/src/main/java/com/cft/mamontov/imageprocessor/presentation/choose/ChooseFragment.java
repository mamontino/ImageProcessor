package com.cft.mamontov.imageprocessor.presentation.choose;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.databinding.FragmentChooseBinding;

import com.cft.mamontov.imageprocessor.R;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

public class ChooseFragment extends DaggerDialogFragment {

    public static final int REQUEST_CODE_GALLERY = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    public static final int REQUEST_CODE_URL = 3;

    private OnSourceListener mOnSourceListener;
    private FragmentChooseBinding mBinding;

    @Inject
    public ChooseFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnSourceListener = (OnSourceListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnSourceListener");
        }
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
            mOnSourceListener.onSourceSelected(REQUEST_CODE_GALLERY);
            this.dismiss();
        });

        mBinding.chooseFragmentBtnUrl.setOnClickListener(v -> {
            mOnSourceListener.onSourceSelected(REQUEST_CODE_URL);
            this.dismiss();
        });
    }

    public interface OnSourceListener {
        void onSourceSelected(int source);
    }
}
