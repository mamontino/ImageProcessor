package com.mamontino.imageprocessor.mvp.main;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.FragmentMainBinding;
import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class MainFragment extends DaggerFragment implements MainContract.View {

    private boolean hasImage = false;
    private FragmentMainBinding mBinding;

    @Inject
    MainContract.Presenter mPresenter;

    @Inject
    ChooseFragment mChooseFragment;

    @Inject
    public MainFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.initView(this);
    }


    @Override
    public void onDestroy() {
        mPresenter.destroyView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.addImageButton.setOnClickListener(v -> showChooseFragment());
    }

    private void showChooseFragment() {
        if (getActivity() != null) {
            mChooseFragment.show(getActivity().getFragmentManager(), mChooseFragment.getTag());
        }
    }

    public void loadImageFromUri(String uri) {
//        mPresenter.getImageFromUrl(uri);
    }

    public void setCurrentImage(Bitmap bitmap) {
        hasImage = true;
        mBinding.addImageButton.setVisibility(View.GONE);
        mBinding.mainImage.setImageBitmap(bitmap);
    }
}
