package com.cft.mamontov.imageprocessor.di.module;

import com.cft.mamontov.imageprocessor.di.scope.ActivityScoped;
import com.cft.mamontov.imageprocessor.mvp.main.MainContract;
import com.cft.mamontov.imageprocessor.mvp.main.MainPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class PresenterModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);

    @Provides
    @ActivityScoped
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
