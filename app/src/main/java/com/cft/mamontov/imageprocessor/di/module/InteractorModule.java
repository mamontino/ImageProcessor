package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ServiceScope;
import com.cft.mamontov.imageprocessor.interactors.IImageInteractor;
import com.cft.mamontov.imageprocessor.interactors.ILoadInteractor;
import com.cft.mamontov.imageprocessor.interactors.ImageInteractor;
import com.cft.mamontov.imageprocessor.interactors.LoadInteractor;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class InteractorModule {

    @ServiceScope
    @Binds
    abstract ILoadInteractor provideLoadInteractor(LoadInteractor interactor);

    @Provides
    static IImageInteractor provideImageInteractor(ImageInteractor interactor){
        return interactor;
    };
}
