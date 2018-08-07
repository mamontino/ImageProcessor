package com.mamontino.imageprocessor.mvp;

import android.os.Bundle;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.mvp.choose.ChooseFragment;
import com.mamontino.imageprocessor.mvp.load.LoadFragment;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements ChooseFragment.OnSourceListener,
        LoadFragment.OnUrlListener {

    @Inject
    MainFragment mMainFragment;

    @Inject
    LoadFragment mLoadFragment;

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
        if (source == ChooseFragment.REQUEST_CODE_URL){
            showLoadFragment();
        }
        mMainFragment.loadImage(source);
    }

    private void showLoadFragment() {
       mLoadFragment.show(getFragmentManager(), mLoadFragment.getTag());
    }

    @Override
    public void onUrlSelected(String url) {
        mMainFragment.loadImageUrl(url);
    }
}
