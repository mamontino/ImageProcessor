package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.data.db.ILocalDataSource;
import com.cft.mamontov.imageprocessor.data.network.INetworkDataSource;
import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.di.name.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class Repository implements ILocalDataSource, INetworkDataSource {

    private final ILocalDataSource mLocalDataSource;
    private final INetworkDataSource mRemoteDataSource;

    @Inject
    Repository(@Local ILocalDataSource localDataSource, @Remote INetworkDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mRemoteDataSource.getImageFromUrl(url);
    }
}
