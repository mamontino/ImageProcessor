package com.cft.mamontov.imageprocessor.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cft.mamontov.imageprocessor.data.db.DatabaseHelper;
import com.cft.mamontov.imageprocessor.data.db.DatabaseManager;
import com.cft.mamontov.imageprocessor.data.db.IPDatabase;
import com.cft.mamontov.imageprocessor.data.db.ImageDao;
import com.cft.mamontov.imageprocessor.data.network.ApiService;
import com.cft.mamontov.imageprocessor.data.network.NetworkHelper;
import com.cft.mamontov.imageprocessor.data.network.NetworkManager;
import com.cft.mamontov.imageprocessor.data.preferences.PreferencesHelper;
import com.cft.mamontov.imageprocessor.data.preferences.PreferencesManager;
import com.cft.mamontov.imageprocessor.di.name.Local;
import com.cft.mamontov.imageprocessor.di.name.Remote;
import com.cft.mamontov.imageprocessor.utils.AppConstants;
import com.cft.mamontov.imageprocessor.utils.events.RxBus;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProviderHelper;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProvider;

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
    @Local
    abstract DatabaseHelper provideLocalDataSource(DatabaseManager databaseManager);

    @Singleton
    @Binds
    @Remote
    abstract NetworkHelper provideRemoteDataSource(NetworkManager networkManager);

    @Singleton
    @Binds
    abstract PreferencesHelper providePreferencesHelper(PreferencesManager preferencesManager);


    @Singleton
    @Provides
    static SharedPreferences provideSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    static IPDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), IPDatabase.class, AppConstants.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    static ImageDao provideUserDao(IPDatabase db) {
        return db.userDao();
    }

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
