package com.cft.mamontov.imageprocessor.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class RemoteDataSource implements IRemoteDataSource {

    private final ApiService mApiService;

    @Inject
    RemoteDataSource(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Observable<Response<ResponseBody>> download() {
        return mApiService.download();
    }
}
