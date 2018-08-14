package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.bg.DownloadService;
import com.cft.mamontov.imageprocessor.di.scope.ServiceScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

    @ServiceScoped
    @ContributesAndroidInjector
    abstract DownloadService provideDownloadService();
}
