package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.FragmentScope;
import com.cft.mamontov.imageprocessor.presentation.choose.ChooseFragment;
import com.cft.mamontov.imageprocessor.presentation.load.LoadFragment;
import com.cft.mamontov.imageprocessor.presentation.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract ChooseFragment chooseFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract LoadFragment loadFragment();
}
