package com.mamontino.imageprocessor.mvp.load;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.FragmentLoadImageBinding;
import com.mamontino.imageprocessor.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class LoadFragment extends DaggerFragment{

    private FragmentLoadImageBinding mBinding;

    @Inject
    public LoadFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_load_image,
                container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.fragmentLoadBtn.setOnClickListener(v -> {
            String text = mBinding.fragmentLoadEt.getText().toString().trim();

            if (TextUtils.isEmpty(text)) {
                mBinding.fragmentLoadEt.setHint(R.string.error_empty_url);
                return;
            }

//            TODO: get url from fragment /  06.18.2018
        });
    }
}
