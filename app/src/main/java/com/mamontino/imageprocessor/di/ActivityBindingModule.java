package com.mamontino.imageprocessor.di;

import com.mamontino.imageprocessor.main.MainActivity;
import com.mamontino.imageprocessor.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
