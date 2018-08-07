package com.mamontino.imageprocessor.mvp.load;

import com.mamontino.imageprocessor.di.FragmentScoped;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoadModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoadFragment mainFragment();
}
