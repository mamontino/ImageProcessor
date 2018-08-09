package com.cft.mamontov.imageprocessor.presentation.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.Bitmap;

import com.cft.mamontov.imageprocessor.use_case.MainInteractor;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.net.URI;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends AndroidViewModel {

    private static final int mMinDelay = 5;
    private static final int mMaxDelay = 30;

    private boolean isRunning = false;

    private MainInteractor mInteractor;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mDisposable;

    @Inject
    MainViewModel(Application context, MainInteractor interactor, BaseSchedulerProvider scheduler, CompositeDisposable disposable) {
        super(context);
        mInteractor = interactor;
        mScheduler = scheduler;
        mDisposable = disposable;
    }

    private long getLongProcessing() {
        return mMinDelay + (long) (Math.random() * mMaxDelay - mMinDelay);
    }

    public void getImageFromUrl(URI url) {

    }

    public void loadImageList() {
    }

    public void transformImage(Bitmap bitmap, Transformation transformation) {
//        long val = getLongProcessing();
//        mDisposable.add(Observable.intervalRange(0L, val, 1, 1, TimeUnit.SECONDS)
//                .subscribeOn(mScheduler.io())
//                .observeOn(mScheduler.ui())
//                .subscribe(v -> mMainView.updateProcessing(v.intValue()),
//                        Throwable::printStackTrace,
//                        () -> transform(bitmap, transformation)));
    }

    private void transform(Bitmap bitmap, Transformation transformation) {
//        Bitmap picture = transformation.transform(bitmap);
//        mMainView.addItem(new TransformedImage(picture));
    }

}
