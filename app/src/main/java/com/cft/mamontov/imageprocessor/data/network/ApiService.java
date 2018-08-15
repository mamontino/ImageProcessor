package com.cft.mamontov.imageprocessor.data.network;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    @Streaming
    Observable<Response<ResponseBody>> getImageFromUrl(@Url String url);
}
