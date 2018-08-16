package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.data.db.ILocalDataSource;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.data.network.INetworkDataSource;
import com.cft.mamontov.imageprocessor.data.preferences.PreferencesHelper;
import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.di.name.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class Repository implements ILocalDataSource, INetworkDataSource, PreferencesHelper {

    private final ILocalDataSource mLocalDataSource;
    private final INetworkDataSource mRemoteDataSource;
    private final PreferencesHelper mPref;

    @Inject
    Repository(@Local ILocalDataSource localDataSource, @Remote INetworkDataSource remoteDataSource,
               PreferencesHelper pref) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mPref = pref;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mRemoteDataSource.getImageFromUrl(url);
    }

    @Override
    public Flowable<List<TransformedImage>> getOrderedImages() {
        return mLocalDataSource.getOrderedImages();
    }

    @Override
    public Completable insertImage(TransformedImage image) {
        return mLocalDataSource.insertImage(image);
    }

    @Override
    public void setCurrentImage(String image) {
        mPref.setCurrentImage(image);
    }

    @Override
    public String getCurrentImage() {
        return mPref.getCurrentImage();
    }
}
