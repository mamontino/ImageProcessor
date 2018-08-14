package com.cft.mamontov.imageprocessor.data.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface IRemoteDataSource {
    Observable<Response<ResponseBody>> download();
}
