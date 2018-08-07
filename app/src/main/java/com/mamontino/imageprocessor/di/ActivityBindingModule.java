package com.mamontino.imageprocessor.di;

import com.mamontino.imageprocessor.mvp.MainActivity;
import com.mamontino.imageprocessor.mvp.choose.ChooseModule;
import com.mamontino.imageprocessor.mvp.load.LoadModule;
import com.mamontino.imageprocessor.mvp.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class, ChooseModule.class, LoadModule.class})
    abstract MainActivity mainActivity();
}
