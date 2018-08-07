package com.mamontino.imageprocessor.mvp.main;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.FragmentMainBinding;
import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class MainFragment extends DaggerFragment implements MainContract.View {

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
//        TODO: Add click's for views / 07.08.2018
        mBinding.addImageButton.setOnClickListener(v -> showChooseFragment());

    }

    void getImageFromUrl(String url) {
       mPresenter.getImageFromUrl(url);
    }

    private void showChooseFragment() {
        if (getActivity() != null) {
            mChooseFragment.show(getActivity().getFragmentManager(), mChooseFragment.getTag());
        }

    }

    public void loadImage(int source) {
        switch (source) {
            case ChooseFragment.REQUEST_CODE_CAMERA:
                getImageFromCamera();
                break;
            case ChooseFragment.REQUEST_CODE_LOCAL:
                getImageLocal();
                break;
        }

    }

    private void getImageLocal() {
        Toast.makeText(getContext(), "getImageLocal", Toast.LENGTH_SHORT).show();
    }

    private void getImageFromCamera() {
        Toast.makeText(getContext(), "getImageFromCamera", Toast.LENGTH_SHORT).show();
    }

    public void setImage(Bitmap bitmap) {

    }

    public void loadImageUrl(String url) {

    }
}
