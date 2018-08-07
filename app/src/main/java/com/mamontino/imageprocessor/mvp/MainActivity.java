package com.mamontino.imageprocessor.mvp;

import android.os.Bundle;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements ChooseFragment.OnSourceListener {

    @Inject
    MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMainFragment();
    }

    private void showMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.main_container, mMainFragment)
                .commit();
    }

    @Override
    public void onSourceSelected(int source) {
        mMainFragment.loadImage(source);
    }
}
