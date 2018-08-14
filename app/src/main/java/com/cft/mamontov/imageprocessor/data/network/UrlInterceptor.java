package com.cft.mamontov.imageprocessor.data.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UrlInterceptor implements Interceptor {

    private static UrlInterceptor sInterceptor;
    private String mScheme;
    private String mHost;

    public static UrlInterceptor get() {
        if (sInterceptor == null) {
            sInterceptor = new UrlInterceptor();
        }
        return sInterceptor;
    }

    private UrlInterceptor() {
    }

    public void setInterceptor(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        assert httpUrl != null;
        mScheme = httpUrl.scheme();
        mHost = httpUrl.host();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        if (mScheme != null && mHost != null) {
            HttpUrl newUrl = original.url().newBuilder()
                    .scheme(mScheme)
                    .host(mHost)
                    .build();
            original = original.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(original);
    }
}
