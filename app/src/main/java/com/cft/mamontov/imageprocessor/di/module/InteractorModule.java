package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ServiceScope;
import com.cft.mamontov.imageprocessor.interactors.ImageInteractorHelper;
import com.cft.mamontov.imageprocessor.interactors.LoadInteractorHelper;
import com.cft.mamontov.imageprocessor.interactors.ImageInteractor;
import com.cft.mamontov.imageprocessor.interactors.LoadInteractor;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class InteractorModule {

    @ServiceScope
    @Binds
    abstract LoadInteractorHelper provideLoadInteractor(LoadInteractor interactor);

    @Binds
    abstract ImageInteractorHelper provideImageInteractor(ImageInteractor interactor);
}
