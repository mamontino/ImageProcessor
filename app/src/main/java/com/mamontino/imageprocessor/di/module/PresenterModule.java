package com.mamontino.imageprocessor.di.module;

import com.mamontino.imageprocessor.di.scope.ActivityScoped;
import com.mamontino.imageprocessor.mvp.main.MainContract;
import com.mamontino.imageprocessor.mvp.main.MainPresenter;

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
