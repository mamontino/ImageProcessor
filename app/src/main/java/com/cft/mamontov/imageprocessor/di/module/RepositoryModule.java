package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.cft.mamontov.imageprocessor.data.db.ILocalDataSource;
import com.cft.mamontov.imageprocessor.data.network.IRemoteDataSource;
import com.cft.mamontov.imageprocessor.data.network.ApiService;
import com.cft.mamontov.imageprocessor.data.network.RemoteDataSource;
import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.data.db.IPDatabase;
import com.cft.mamontov.imageprocessor.data.db.LocalDataSource;
import com.cft.mamontov.imageprocessor.data.db.UserDao;
import com.cft.mamontov.imageprocessor.di.name.Remote;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;
import com.cft.mamontov.imageprocessor.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
abstract public class RepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract ILocalDataSource provideLocalDataSource(LocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract IRemoteDataSource provideRemoteDataSource(RemoteDataSource dataSource);


    @Singleton
    @Provides
    static IPDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), IPDatabase.class, "Users.db")
                .build();
    }

    @Singleton
    @Provides
    static UserDao provideUserDao(IPDatabase db) {
        return db.userDao();
    }

    @Provides
    @Singleton
    public static ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    static BaseSchedulerProvider provideSchedulers(){
        return SchedulerProvider.getInstance();
    }
}
