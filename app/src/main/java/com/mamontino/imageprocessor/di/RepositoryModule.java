package com.mamontino.imageprocessor.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mamontino.imageprocessor.data.source.DataSource;
import com.mamontino.imageprocessor.data.source.db.IPDatabase;
import com.mamontino.imageprocessor.data.source.db.LocalDataSource;
import com.mamontino.imageprocessor.data.source.db.UserDao;
import com.mamontino.imageprocessor.data.source.network.RemoteDataSource;


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
