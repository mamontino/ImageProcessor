package com.mamontino.imageprocessor.di.module;

import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.interactors.MainInteractor;
import com.mamontino.imageprocessor.interactors.MainInteractorContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InteractorModule{

    @ActivityScoped
    @Binds
    abstract MainInteractorContract mainInteractor(MainInteractor interactor);
}
