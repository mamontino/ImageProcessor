package com.mamontino.imageprocessor.mvp.main;

import com.mamontino.imageprocessor.source.Repository;

import javax.inject.Inject;

public final class MainInteractor implements MainInteractorContract {

    private Repository mRepository;

    @Inject
    MainInteractor(Repository repository) {
        mRepository = repository;
    }

    @Override
    public void getPresenter(Object presenter) {
        
    }
}
