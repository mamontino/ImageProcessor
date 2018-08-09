package com.mamontino.imageprocessor.di.module;

import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.mvp.MainActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FragmentModule.class, PresenterModule.class, InteractorModule.class})
    abstract MainActivity mainActivity();
}
