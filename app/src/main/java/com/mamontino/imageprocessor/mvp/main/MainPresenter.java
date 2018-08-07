package com.mamontino.imageprocessor.mvp.main;

import android.support.annotation.Nullable;

import java.net.URI;

import javax.inject.Inject;

public final class MainPresenter implements MainContract.Presenter {

    private MainInteractor mInteractor;

    @Nullable
    private MainContract.View mMainView;

    @Inject
    MainPresenter(MainInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void initView(MainContract.View view) {

    }

    @Override
    public void destroyView() {

    }

    private int getLongerProcessing(){
       return 5 + (int) (Math.random() * 30);
    }

    @Override
    public void getImageFromUrl(URI url) {

    }
}
