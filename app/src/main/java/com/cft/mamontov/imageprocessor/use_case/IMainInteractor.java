package com.cft.mamontov.imageprocessor.use_case;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface IMainInteractor {
    Single<Response<ResponseBody> > getImageFromUrl(String url);
}
