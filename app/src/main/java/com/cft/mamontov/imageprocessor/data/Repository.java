package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.data.db.ILocalDataSource;
import com.cft.mamontov.imageprocessor.data.network.IRemoteDataSource;
import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.di.name.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class Repository implements ILocalDataSource, IRemoteDataSource {

    private final ILocalDataSource mLocalDataSource;
    private final IRemoteDataSource mRemoteDataSource;

    @Inject
    Repository(@Local ILocalDataSource localDataSource, @Remote IRemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Response<ResponseBody>> download() {
        return mRemoteDataSource.download();
    }
}
