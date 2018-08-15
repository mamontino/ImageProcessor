package com.cft.mamontov.imageprocessor.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.exceptions.SourceNotFoundException;
import com.cft.mamontov.imageprocessor.presentation.choose.ChooseFragment;
import com.cft.mamontov.imageprocessor.presentation.load.LoadFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements
        ChooseFragment.OnSourceListener, LoadFragment.OnUrlListener {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSourceSelected(int source) {
        if (source == ChooseFragment.REQUEST_CODE_URL) {
            showLoadFragment();
        } else if (source == ChooseFragment.REQUEST_CODE_CAMERA) {
            mMainFragment.loadImage(ChooseFragment.REQUEST_CODE_CAMERA);
        } else if (source == ChooseFragment.REQUEST_CODE_GALLERY) {
            mMainFragment.loadImage(ChooseFragment.REQUEST_CODE_GALLERY);
        } else throw new SourceNotFoundException(source + " is not found");
    }

    @Override
    public void onUrlSelected(String url) {
        mMainFragment.loadImage(url);
    }

    private void showMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, mMainFragment)
                .commit();
    }

    private void showLoadFragment() {
        mLoadFragment.show(getFragmentManager(), mLoadFragment.getTag());
    }
}
