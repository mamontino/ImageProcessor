package com.mamontino.imageprocessor.di;

import android.app.Application;

import com.mamontino.imageprocessor.base.IPApplication;
import com.mamontino.imageprocessor.di.module.ActivityModule;
import com.mamontino.imageprocessor.di.module.ApplicationModule;
import com.mamontino.imageprocessor.di.module.NetworkModule;
import com.mamontino.imageprocessor.di.module.RepositoryModule;
import com.mamontino.imageprocessor.source.Repository;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class, ActivityModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<IPApplication> {

    Repository getRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
