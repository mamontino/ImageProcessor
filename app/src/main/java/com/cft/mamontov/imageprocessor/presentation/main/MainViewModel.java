package com.cft.mamontov.imageprocessor.presentation.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.use_case.MainInteractor;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
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

    private static final int mMinDelay = 5;
    private static final int mMaxDelay = 30;

    private MainInteractor mInteractor;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mDisposable;
    private int mId = 0;

    private List<TransformedImage> mList;
    private Bitmap mCurrentPicture;
    private String mCurrentPicturePath;
    private boolean hasImage;

    @Inject
    MainViewModel(MainInteractor interactor, BaseSchedulerProvider scheduler,
                  CompositeDisposable disposable) {
        mInteractor = interactor;
        mScheduler = scheduler;
        mDisposable = disposable;
        mList = Collections.synchronizedList(new ArrayList<>());
    }

    MutableLiveData<Bitmap> updateCurrentPicture = new MutableLiveData<>();
    MutableLiveData<TransformedImage> updateProcessing = new MutableLiveData<>();
    MutableLiveData<TransformedImage> getItem = new MutableLiveData<>();

    public List<TransformedImage> getList() {
        return mList;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean has) {
        hasImage = has;
    }

    public void setCurrentPicture(Bitmap bitmap) {
        this.mCurrentPicture = bitmap;
        hasImage = true;
        updateCurrentPicture.postValue(mCurrentPicture);
    }

    public Bitmap getCurrentPicture() {
        return mCurrentPicture;
    }

    public void setCurrentPicturePath(String path) {
        mCurrentPicturePath = path;
    }

    public String getCurrentPicturePath() {
        return mCurrentPicturePath;
    }

    public void transformImage(Transformation transformation) {
        mId++;
        TransformedImage image = new TransformedImage(mId);
        mList.add(image);
        getItem.postValue(image);
        mDisposable.add(Observable.intervalRange(1, 10, 0,
                getLongProcessing()/10, TimeUnit.SECONDS)
                .subscribeOn(mScheduler.newThread())
                .observeOn(mScheduler.ui())
                .subscribe(v -> {
                    Log.e("ViewModel: ", " updateProcessing.postValue: " + v);
                    image.setProgress(v.intValue()*10);
                    updateProcessing.postValue(image);
                }, Throwable::printStackTrace, () -> applyTransformation(transformation, image)));
    }

    public void removeItem(int position) {
        mList.remove(position);
    }

    public void getImageFromUrl(String url, ImageView view) {
    }

    private long getLongProcessing() {
        return mMinDelay + (long) (Math.random() * mMaxDelay - mMinDelay);
    }

    private synchronized void applyTransformation(Transformation transformation, TransformedImage image) {
        mDisposable.add(Single.just(transformation.transform(mCurrentPicture))
                .subscribeOn(mScheduler.newThread())
                .observeOn(mScheduler.ui())
                .subscribe(bitmap -> {
                            image.setBitmap(bitmap);
                            mList.add(mList.size() - 1, image);
                            updateProcessing.postValue(image);
                            Log.e("ViewModel: ", "getItem.postValue(image)");
                        },
                        throwable -> Log.e("ViewModel: ", throwable.getLocalizedMessage())));
    }
}
