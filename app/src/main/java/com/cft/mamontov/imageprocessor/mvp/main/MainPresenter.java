package com.cft.mamontov.imageprocessor.mvp.main;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.cft.mamontov.imageprocessor.interactors.MainInteractor;
import com.cft.mamontov.imageprocessor.source.models.TransformedImage;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public final class MainPresenter implements MainContract.Presenter {

    private static final int mMinDelay = 5;
    private static final int mMaxDelay = 30;

    private boolean isRunning = false;

    private MainInteractor mInteractor;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mDisposable;

    @Nullable
    private MainContract.View mMainView;

    @Inject
    MainPresenter(MainInteractor interactor, BaseSchedulerProvider scheduler, CompositeDisposable disposable) {
        mInteractor = interactor;
        mScheduler = scheduler;
        mDisposable = disposable;
    }

    private long getLongProcessing() {
        return mMinDelay + (long) (Math.random() * mMaxDelay - mMinDelay);
    }

    @Override
    public void getImageFromUrl(URI url) {

    }

    @Override
    public void loadImageList() {
    }

    @Override
    public void transformImage(Bitmap bitmap, Transformation transformation) {
        long val = getLongProcessing();
        mDisposable.add(Observable.intervalRange(0L, val, 1, 1, TimeUnit.SECONDS)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(v -> mMainView.updateProcessing(v.intValue()),
                        Throwable::printStackTrace,
                        () -> transform(bitmap, transformation)));
    }

    private void transform(Bitmap bitmap, Transformation transformation) {
        Bitmap picture = transformation.transform(bitmap);
        mMainView.addItem(new TransformedImage(picture));
    }

    @Override
    public void initView(MainContract.View view) {
        mMainView = view;
    }

    @Override
    public void destroyView() {
        mMainView = null;
    }
}
