package com.mamontino.imageprocessor.mvp;

import android.os.Bundle;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.mvp.main.MainFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
