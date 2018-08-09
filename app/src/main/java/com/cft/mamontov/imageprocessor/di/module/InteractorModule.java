package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ActivityScoped;
import com.cft.mamontov.imageprocessor.interactors.MainInteractor;
import com.cft.mamontov.imageprocessor.interactors.MainInteractorContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InteractorModule{

    @ActivityScoped
    @Binds
    abstract MainInteractorContract mainInteractor(MainInteractor interactor);
}
