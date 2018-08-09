package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ActivityScoped;
import com.cft.mamontov.imageprocessor.use_case.IMainInteractor;
import com.cft.mamontov.imageprocessor.use_case.MainInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InteractorModule{

    @ActivityScoped
    @Binds
    abstract IMainInteractor mainInteractor(MainInteractor interactor);
}
