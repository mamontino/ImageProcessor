package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.FragmentScoped;
import com.cft.mamontov.imageprocessor.mvp.choose.ChooseFragment;
import com.cft.mamontov.imageprocessor.mvp.load.LoadFragment;
import com.cft.mamontov.imageprocessor.mvp.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseFragment chooseFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoadFragment loadFragment();
}
