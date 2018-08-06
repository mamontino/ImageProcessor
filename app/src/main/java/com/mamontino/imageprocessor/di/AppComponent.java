package com.mamontino.imageprocessor.di;

import android.app.Application;

import com.mamontino.imageprocessor.IPApplication;
import com.mamontino.imageprocessor.dat.Repository;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, ApplicationModule.class, ActivityBindingModule.class,
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
