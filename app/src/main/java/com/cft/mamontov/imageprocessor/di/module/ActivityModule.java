package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ActivityScoped;
import com.cft.mamontov.imageprocessor.mvp.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FragmentModule.class, PresenterModule.class, InteractorModule.class})
    abstract MainActivity mainActivity();
}
