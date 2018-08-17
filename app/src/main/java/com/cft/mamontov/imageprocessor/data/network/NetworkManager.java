package com.cft.mamontov.imageprocessor.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class NetworkManager implements NetworkHelper {

    private final ApiService mApiService;

    @Inject
    NetworkManager(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mApiService.getImageFromUrl(url);
    }
}
