package com.mamontino.imageprocessor.mvp.main;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.FragmentMainBinding;
import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;
import com.mamontino.imageprocessor.source.models.TransformedImage;
import com.mamontino.imageprocessor.utils.tranformation.InvertColorTransformation;
import com.mamontino.imageprocessor.utils.tranformation.MirrorTransformation;
import com.mamontino.imageprocessor.utils.tranformation.RotateTransformation;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class MainFragment extends DaggerFragment implements MainContract.View {

    private boolean hasImage = false;
    private FragmentMainBinding mBinding;
    private ImageListAdapter mAdapter;
    private Bitmap mCurrentPicture = null;

    @Inject
    MainContract.Presenter mPresenter;

    @Inject
    ChooseFragment mChooseFragment;

    @Inject
    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.fragmentMainBtnExif.setOnClickListener(v -> showExifFragment());
        mBinding.addImageButton.setOnClickListener(v -> showChooseFragment());
        mBinding.fragmentMainBtnInvertColors.setOnClickListener(v -> invertColorsClicked());
        mBinding.fragmentMainBtnMirrorImage.setOnClickListener(v -> mirrorImageClicked());
        mBinding.fragmentMainBtnRotate.setOnClickListener(v -> rotateImageClicked());
        initRecyclerView();
    }

    private void showExifFragment() {

    }

    private void rotateImageClicked() {
        if (!hasImage){
            showChooseFragment();
            return;
        }
        mPresenter.transformImage(mCurrentPicture, new RotateTransformation());
    }

    private void mirrorImageClicked() {
        if (!hasImage){
            showChooseFragment();
            return;
        }
        mPresenter.transformImage(mCurrentPicture, new MirrorTransformation());
    }

    private void invertColorsClicked() {
        if (!hasImage){
            showChooseFragment();
            return;
        }
        mPresenter.transformImage(mCurrentPicture, new InvertColorTransformation());
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.fragmentMainRv.setLayoutManager(layoutManager);
        mBinding.fragmentMainRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImageListAdapter(getContext());
        mBinding.fragmentMainRv.setAdapter(mAdapter);
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
        mCurrentPicture = bitmap;
        mBinding.addImageButton.setVisibility(View.GONE);
        mBinding.mainImage.setImageBitmap(bitmap);
    }

    @Override
    public void showProgressIndicator(boolean b) {
        mAdapter.showProgressIndicator(b);
    }

    @Override
    public void addItems(List<TransformedImage> list) {
        mAdapter.setItems(list);
        mBinding.fragmentMainRv.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void addItem(TransformedImage picture) {
        mAdapter.addItem(picture);
        mBinding.fragmentMainRv.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void updateProcessing(int val) {
        mAdapter.updateProcessing(val);
    }
}
