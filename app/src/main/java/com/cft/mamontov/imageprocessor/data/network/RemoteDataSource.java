package com.cft.mamontov.imageprocessor.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class RemoteDataSource implements INetworkDataSource {

    private final ApiService mApiService;

    @Inject
    RemoteDataSource(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Single<Response<ResponseBody>> getImageFromUrl(String url) {
        return mApiService.getImageFromUrl(url);
    }
}
