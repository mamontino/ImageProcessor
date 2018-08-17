package com.cft.mamontov.imageprocessor.interactors;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface LoadInteractorHelper {

    Observable<Response<ResponseBody>> getImageFromUrl(String url);
}
