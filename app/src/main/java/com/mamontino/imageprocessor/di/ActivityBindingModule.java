package com.mamontino.imageprocessor.di;

import com.mamontino.imageprocessor.mvp.MainActivity;
import com.mamontino.imageprocessor.mvp.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
