package com.cft.mamontov.imageprocessor.presentation.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.use_case.MainInteractor;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {

    private static final int mMinDelay = 5;
    private static final int mMaxDelay = 30;

    private MainInteractor mInteractor;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mDisposable;

    private List<TransformedImage> mList;
    private TransformedImage mCurrentPicture;
    private String mCurrentPicturePath;
    private boolean hasImage;

    @Inject
    MainViewModel(MainInteractor interactor, BaseSchedulerProvider scheduler,
                  CompositeDisposable disposable) {
        mInteractor = interactor;
        mScheduler = scheduler;
        mDisposable = disposable;
        mList = new ArrayList<>();
    }

    MutableLiveData<Integer> updateProcessing = new MutableLiveData<>();
    MutableLiveData<Boolean> startProcessing = new MutableLiveData<>();
    MutableLiveData<TransformedImage> Item = new MutableLiveData<>();
    MutableLiveData<TransformedImage> updateCurrentPicture = new MutableLiveData<>();
    MutableLiveData<List<TransformedImage>> getHistory = new MutableLiveData<>();

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean has) {
        hasImage = has;
    }

    public void setCurrentPicture(TransformedImage picture) {
        this.mCurrentPicture = picture;
        hasImage = true;
        updateCurrentPicture.postValue(mCurrentPicture);
    }

    public TransformedImage getCurrentPicture() {
        return mCurrentPicture;
    }

    public void setCurrentPicturePath(String path) {
        mCurrentPicturePath = path;
    }

    public String getCurrentPicturePath() {
        return mCurrentPicturePath;
    }

    public void transformImage(Transformation transformation) {
        long val = getLongProcessing();
        mDisposable.add(Observable.intervalRange(0L, val, 1, 1, TimeUnit.SECONDS, mScheduler.newThread())
                .subscribeOn(mScheduler.ui())
                .subscribe(v -> updateProcessing.postValue(v.intValue()),
                        Throwable::printStackTrace,
                        () -> applyTransformation(transformation)));
    }

    public void removeItem(int position) {
        mList.remove(position);
    }

    public void getImageFromUrl(String url, ImageView view) {

    }

    private void onImageLoaded(Bitmap bitmap) {
        updateCurrentPicture.postValue(new TransformedImage(bitmap));
        mList.add(new TransformedImage(bitmap));
    }

    private long getLongProcessing() {
//        return mMinDelay + (long) (Math.random() * mMaxDelay - mMinDelay);
        return 1;
    }

    private void applyTransformation(Transformation transformation) {
        Bitmap picture = transformation.transform(mCurrentPicture.getBitmap());
        mList.add(new TransformedImage(picture));
        Item.postValue(new TransformedImage(picture));
    }

    public void getImageHistory() {
        getHistory.postValue(mList);
    }
}
