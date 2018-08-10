package com.cft.mamontov.imageprocessor.use_case;

import android.graphics.Bitmap;

import com.cft.mamontov.imageprocessor.data.Repository;

import java.net.URI;

import javax.inject.Inject;

import io.reactivex.Single;

public final class MainInteractor implements IMainInteractor {

    private Repository mRepository;

    @Inject
    MainInteractor(Repository repository) {
        mRepository = repository;
    }

    public Single<Bitmap> getPictureFromUri(URI url) {
        return null;
    }
}
