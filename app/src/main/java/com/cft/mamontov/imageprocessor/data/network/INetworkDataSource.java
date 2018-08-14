package com.cft.mamontov.imageprocessor.data.network;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface INetworkDataSource {
    Single<Response<ResponseBody>> getImageFromUrl(String url);
}
