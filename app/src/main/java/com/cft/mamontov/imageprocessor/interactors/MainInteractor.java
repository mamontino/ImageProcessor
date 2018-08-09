package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.source.Repository;

import javax.inject.Inject;

public final class MainInteractor implements MainInteractorContract {

    private Repository mRepository;

    @Inject
    MainInteractor(Repository repository) {
        mRepository = repository;
    }

}