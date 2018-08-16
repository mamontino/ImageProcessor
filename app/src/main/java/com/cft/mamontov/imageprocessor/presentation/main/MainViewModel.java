package com.cft.mamontov.imageprocessor.presentation.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.utils.rx.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {

    private final BaseSchedulerProvider mScheduler;
    private final CompositeDisposable mDisposable;
    private final List<TransformedImage> mList;

    private int mId = 0;
    private boolean hasImage;
    private String mUrl;
    private String mCurrentPicturePath;
    private Bitmap mCurrentPicture;

    @Inject
    MainViewModel(BaseSchedulerProvider scheduler, CompositeDisposable disposable) {
        mScheduler = scheduler;
        mDisposable = disposable;
        mList = Collections.synchronizedList(new ArrayList<>());
    }

    MutableLiveData<Bitmap> updateCurrentPicture = new MutableLiveData<>();
    MutableLiveData<TransformedImage> updateProcessing = new MutableLiveData<>();
    MutableLiveData<TransformedImage> postItem = new MutableLiveData<>();
    MutableLiveData<String> postError = new MutableLiveData<>();

    List<TransformedImage> getList() {
        return mList;
    }

    boolean isHasImage() {
        return hasImage;
    }

    void setCurrentPicture(Bitmap bitmap) {
        this.mCurrentPicture = bitmap;
        hasImage = true;
        updateCurrentPicture.postValue(mCurrentPicture);
    }

    Bitmap getCurrentPicture() {
        return mCurrentPicture;
    }

    void setCurrentPicturePath(String path) {
        mCurrentPicturePath = path;
    }

    String getCurrentPicturePath() {
        return mCurrentPicturePath;
    }

    void removeItem(int position) {
        mList.remove(position);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    void transformImage(Transformation transformation) {
        mId++;
        TransformedImage image = new TransformedImage(mId);
        mList.add(0, image);
        postItem.postValue(image);
        mDisposable.add(Observable.intervalRange(1, 10, 0,
                getLongProcessing() / 10, TimeUnit.SECONDS)
                .subscribeOn(mScheduler.newThread())
                .observeOn(mScheduler.ui())
                .subscribe(v -> {
                    image.setProgress(v.intValue() * 10);
                    updateProcessing.postValue(image);
                }, Throwable::printStackTrace, () -> applyTransformation(transformation, image)));
    }

    private long getLongProcessing() {
        int minDelay = 5;
        int maxDelay = 30;
        return minDelay + (long) (Math.random() * maxDelay - minDelay);
    }

    private synchronized void applyTransformation(Transformation transformation, TransformedImage image) {
        mDisposable.add(Single.just(transformation.transform(mCurrentPicture))
                .subscribeOn(mScheduler.newThread())
                .observeOn(mScheduler.ui())
                .subscribe(bitmap -> {
                            image.setBitmap(bitmap);
                            mList.set(getListPosition(image.getId()), image);
                            updateProcessing.postValue(image);
                        },
                        throwable -> postError.postValue(throwable.getMessage())));
    }

    private int getListPosition(int id) {
        synchronized (mList) {
            TransformedImage image;
            for (int i = 0; i < mList.size(); i++) {
                image = mList.get(i);
                if (image.getId() == id) {
                    return i;
                }
            }
            return -1;
        }
    }
}
