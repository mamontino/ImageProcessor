package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;

import com.cft.mamontov.imageprocessor.utils.ErrorHandler;
import com.cft.mamontov.imageprocessor.utils.NetworkChecker;
import com.cft.mamontov.imageprocessor.data.network.interceptor.NetworkStateInterceptor;
import com.cft.mamontov.imageprocessor.data.network.interceptor.UpdateProgressInterceptor;
import com.cft.mamontov.imageprocessor.di.name.OkHttpNetworkInteceptor;
import com.cft.mamontov.imageprocessor.utils.AppConstants;
import com.cft.mamontov.imageprocessor.utils.events.RxBus;
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
    public static OkHttpClient provideOkHttpClient(Cache cache, NetworkChecker networkChecker, RxBus rxBus,
                                                   @OkHttpNetworkInteceptor List<Interceptor> networkInterceptors) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkStateInterceptor(networkChecker));
        okHttpBuilder.addInterceptor(new UpdateProgressInterceptor(rxBus));

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
                .baseUrl(AppConstants.BASE_URL)
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
}
