package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.data.network.NetworkHelper;
import com.cft.mamontov.imageprocessor.di.name.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class Repository implements NetworkHelper {

    private NetworkHelper mRemoteDataSource;

    @Inject
    Repository(@Remote NetworkHelper remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mRemoteDataSource.getImageFromUrl(url);
    }
}
