package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.di.name.Remote;
import com.cft.mamontov.imageprocessor.source.DataSource;
import com.cft.mamontov.imageprocessor.source.db.IPDatabase;
import com.cft.mamontov.imageprocessor.source.db.LocalDataSource;
import com.cft.mamontov.imageprocessor.source.db.UserDao;
import com.cft.mamontov.imageprocessor.source.network.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract public class RepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract DataSource provideLocalDataSource(LocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract DataSource provideRemoteDataSource(RemoteDataSource dataSource);

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
}
