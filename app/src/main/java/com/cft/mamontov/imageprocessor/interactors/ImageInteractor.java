package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class ImageInteractor implements ImageInteractorHelper {

    private Repository mRepository;
    private SchedulerProviderHelper mScheduler;

    @Inject
    public ImageInteractor(Repository repository, SchedulerProviderHelper scheduler) {
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

    @Override
    public void setCurrentImage(String image) {
        mRepository.setCurrentImage(image);
    }

    @Override
    public String getCurrentImage() {
        return mRepository.getCurrentImage();
    }
}
