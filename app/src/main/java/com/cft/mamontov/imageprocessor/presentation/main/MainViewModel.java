package com.cft.mamontov.imageprocessor.presentation.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.use_case.MainInteractor;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {

    private static final int mMinDelay = 5;
    private static final int mMaxDelay = 30;

    private boolean isRunning = false;

    private MainInteractor mInteractor;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mDisposable;

    @Inject
    MainViewModel(MainInteractor interactor, BaseSchedulerProvider scheduler, CompositeDisposable disposable) {
        mInteractor = interactor;
        mScheduler = scheduler;
        mDisposable = disposable;
    }

    MutableLiveData<Integer> updateProcessing = new MutableLiveData<>();
    MutableLiveData<Boolean> startProcessing = new MutableLiveData<>();
    MutableLiveData<TransformedImage> getItem = new MutableLiveData<>();
    MutableLiveData<TransformedImage> setCurrentPicture = new MutableLiveData<>();

    public void getImageFromUrl(URI url) {
        mDisposable.add(mInteractor.getPictureFromUri(url)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(v -> setCurrentPicture.postValue(new TransformedImage(v)),
                        Throwable::printStackTrace));
    }

    private long getLongProcessing() {
        return mMinDelay + (long) (Math.random() * mMaxDelay - mMinDelay);
    }

    public void transformImage(Bitmap bitmap, Transformation transformation) {
        long val = getLongProcessing();
        mDisposable.add(Observable.intervalRange(0L, val, 1, 1, TimeUnit.SECONDS)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(v -> updateProcessing.postValue(v.intValue()),
                        Throwable::printStackTrace,
                        () -> applyTransformation(bitmap, transformation)));
    }

    private void applyTransformation(Bitmap bitmap, Transformation transformation) {
        Bitmap picture = transformation.transform(bitmap);
        getItem.postValue(new TransformedImage(picture));
    }
}
