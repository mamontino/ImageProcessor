package com.mamontino.imageprocessor.di.module;

import com.mamontino.imageprocessor.di.scope.FragmentScoped;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;
import com.mamontino.imageprocessor.mvp.load.LoadFragment;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

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
