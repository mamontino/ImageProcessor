package com.cft.mamontov.imageprocessor.data.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface ApiService {

    @GET("/download")
    @Streaming
    Observable<Response<ResponseBody>> download();
}
