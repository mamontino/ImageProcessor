package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.bg.LoadingService;
import com.cft.mamontov.imageprocessor.di.scope.ServiceScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

    @ServiceScope
    @ContributesAndroidInjector
    abstract LoadingService provideDownloadService();
}
