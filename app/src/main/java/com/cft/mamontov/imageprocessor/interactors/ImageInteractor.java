package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.utils.rx.BaseSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class ImageInteractor implements IImageInteractor {

    private Repository mRepository;
    private BaseSchedulerProvider mScheduler;

    @Inject
    public ImageInteractor(Repository repository, BaseSchedulerProvider scheduler) {
        this.mRepository = repository;
        this.mScheduler = scheduler;
    }

    @Override
    public Flowable<List<TransformedImage>> getOrderedImages() {
        return mRepository.getOrderedImages().subscribeOn(mScheduler.io());
    }

    @Override
    public Completable insertImage(TransformedImage image) {
       return mRepository.insertImage(image).subscribeOn(mScheduler.io());
    }
}
