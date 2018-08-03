package com.mamontino.imageprocessor.main;

import android.support.annotation.Nullable;

import com.mamontino.imageprocessor.data.source.Repository;

import javax.inject.Inject;

final class MainPresenter implements MainContract.Presenter {

    private Repository mRepository;

    @Nullable
    private MainContract.View mMainView;

    @Inject
    MainPresenter(Repository repository) {
        mRepository = repository;
    }

    @Override
    public void initView(MainContract.View view) {

    }

    @Override
    public void destroyView() {

    }

}
