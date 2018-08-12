package com.cft.mamontov.imageprocessor.di;

import android.app.Fragment;
import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.internal.Beta;

@Beta
public abstract class DaggerBottomShitDialogFragment extends BottomSheetDialogFragment implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(Objects.requireNonNull(getActivity()));
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return childFragmentInjector;
    }
}