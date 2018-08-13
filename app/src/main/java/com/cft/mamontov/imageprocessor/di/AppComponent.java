package com.cft.mamontov.imageprocessor.di;

import android.app.Application;

import com.cft.mamontov.imageprocessor.IPApplication;
import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.di.module.ActivityModule;
import com.cft.mamontov.imageprocessor.di.module.ApplicationModule;
import com.cft.mamontov.imageprocessor.di.module.RepositoryModule;
import com.cft.mamontov.imageprocessor.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {RepositoryModule.class, ApplicationModule.class,
        ActivityModule.class, ViewModelModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<IPApplication> {

    Repository getRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
