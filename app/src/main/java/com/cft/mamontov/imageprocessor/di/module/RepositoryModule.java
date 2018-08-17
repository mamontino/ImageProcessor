package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.data.network.ApiService;
import com.cft.mamontov.imageprocessor.data.network.NetworkHelper;
import com.cft.mamontov.imageprocessor.data.network.NetworkManager;
import com.cft.mamontov.imageprocessor.di.name.Remote;
import com.cft.mamontov.imageprocessor.utils.events.RxBus;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProviderHelper;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

@Module
abstract public class RepositoryModule {

    @Singleton
    @Binds
    @Remote
    abstract NetworkHelper provideRemoteDataSource(NetworkManager networkManager);

    @Singleton
    @Provides
    static ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    static SchedulerProviderHelper provideSchedulers() {
        return SchedulerProvider.getInstance();
    }

    @Provides
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Singleton
    @Provides
    static RxBus provideRxBus() {
        return RxBus.get();
    }
}
