package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;

import com.cft.mamontov.imageprocessor.data.network.ApiService;
import com.cft.mamontov.imageprocessor.data.network.ErrorHandler;
import com.cft.mamontov.imageprocessor.data.network.NetworkChecker;
import com.cft.mamontov.imageprocessor.data.network.NetworkStateInterceptor;
import com.cft.mamontov.imageprocessor.data.network.UrlInterceptor;
import com.cft.mamontov.imageprocessor.di.name.OkHttpNetworkInteceptor;
import com.cft.mamontov.imageprocessor.di.name.OkHttpUrlInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Collections.singletonList;

@Module
public class NetworkModule {

    @OkHttpUrlInterceptor
    @Provides
    @Singleton
    public static UrlInterceptor provideUrlInterceptor(){
        return UrlInterceptor.get();
    }

    @OkHttpNetworkInteceptor
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
                                                   @OkHttpNetworkInteceptor List<Interceptor> networkInterceptors,
                                                   @OkHttpUrlInterceptor UrlInterceptor urlInterceptor) {

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
    public static Retrofit provideRetrofit(Gson gson, RxJava2CallAdapterFactory rxAdapterFactory,
                                           OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapterFactory)
                .baseUrl("https://www.yandex.ru")
                .build();
    }

    @Provides
    @Singleton
    public static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    public static ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
