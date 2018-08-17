package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.data.db.DatabaseHelper;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.data.network.NetworkHelper;
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
public class Repository implements DatabaseHelper, NetworkHelper, PreferencesHelper {

    private DatabaseHelper mLocalDataSource;
    private NetworkHelper mRemoteDataSource;
    private PreferencesHelper mPreferences;

    @Inject
    Repository(@Local DatabaseHelper localDataSource, @Remote NetworkHelper remoteDataSource,
               PreferencesHelper preferences) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mPreferences = preferences;
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
        mPreferences.setCurrentImage(image);
    }

    @Override
    public String getCurrentImage() {
        return mPreferences.getCurrentImage();
    }
}
