package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ServiceScope;
import com.cft.mamontov.imageprocessor.interactors.ILoadInteractor;
import com.cft.mamontov.imageprocessor.interactors.LoadInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InteractorModule {

    @ServiceScope
    @Binds
    abstract ILoadInteractor provideLoadInteractor(LoadInteractor interactor);
}
