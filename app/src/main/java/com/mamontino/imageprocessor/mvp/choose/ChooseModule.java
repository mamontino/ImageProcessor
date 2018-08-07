package com.mamontino.imageprocessor.mvp.choose;

import com.mamontino.imageprocessor.di.FragmentScoped;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChooseModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseFragment mainFragment();
}
