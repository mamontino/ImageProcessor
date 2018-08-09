package com.cft.mamontov.imageprocessor.use_case;

import com.cft.mamontov.imageprocessor.data.Repository;

import javax.inject.Inject;

public final class MainInteractor implements IMainInteractor {

    private Repository mRepository;

    @Inject
    MainInteractor(Repository repository) {
        mRepository = repository;
    }

}
