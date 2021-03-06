package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ActivityScope;
import com.cft.mamontov.imageprocessor.presentation.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {FragmentModule.class, InteractorModule.class})
    abstract MainActivity mainActivity();
}
