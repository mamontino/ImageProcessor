package com.cft.mamontov.imageprocessor.di.module;

import android.arch.lifecycle.ViewModel;

import com.cft.mamontov.imageprocessor.di.name.ViewModelKey;
import com.cft.mamontov.imageprocessor.presentation.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class ViewModelModule {

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel userViewModel(MainViewModel userViewModel);
}