package com.cft.mamontov.imageprocessor.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class IPViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>,
            Provider<ViewModel>> viewModels;

    @Inject
    public IPViewModelFactory(Map<Class<? extends ViewModel>,
            Provider<ViewModel>> viewModels) {
        this.viewModels = viewModels;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Provider<ViewModel> viewModelProvider = viewModels.get(modelClass);

        if (viewModelProvider == null) {
            throw new IllegalArgumentException("model class "
                    + modelClass
                    + " not found");
        }

        return (T) viewModelProvider.get();
    }
}
