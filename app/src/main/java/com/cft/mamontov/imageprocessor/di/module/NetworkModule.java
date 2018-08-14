package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;
import android.support.annotation.NonNull;

import com.cft.mamontov.imageprocessor.data.network.ErrorHandler;
import com.cft.mamontov.imageprocessor.data.network.NetworkChecker;
import com.cft.mamontov.imageprocessor.data.network.interceptors.NetworkStateInterceptor;
import com.cft.mamontov.imageprocessor.data.network.interceptors.UrlInterceptor;
import com.cft.mamontov.imageprocessor.di.name.NetworkInterceptor;
import com.cft.mamontov.imageprocessor.di.name.OkHttpUrlInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static java.util.Collections.singletonList;

@Module
public class NetworkModule {

    @OkHttpUrlInterceptor
    @Provides
    @Singleton
    public static UrlInterceptor provideUrlInterceptor(){
        return UrlInterceptor.get();
    }

    @NetworkInterceptor
    @Provides
    @Singleton
    public static List<Interceptor> provideOkHttpNetworkInterceptors() {
        return singletonList(new StethoInterceptor());
    }

    @Provides
    @Singleton
    public static Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(Cache cache, NetworkChecker networkChecker,
                                     @NonNull List<Interceptor> networkInterceptors, @NonNull UrlInterceptor urlInterceptor) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkStateInterceptor(networkChecker));
        okHttpBuilder.addInterceptor(urlInterceptor);

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        okHttpBuilder.cache(cache);
        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    public static RxJava2CallAdapterFactory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public static ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(RxJava2CallAdapterFactory rxAdapterFactory,
                                           OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapterFactory)
                .baseUrl("dghkdghk")
                .build();
    }
}
