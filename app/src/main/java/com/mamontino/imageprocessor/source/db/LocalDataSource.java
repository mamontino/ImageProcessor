package com.mamontino.imageprocessor.source.db;

import android.support.annotation.NonNull;

import com.mamontino.imageprocessor.source.DataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalDataSource implements DataSource {

    private final UserDao mUserDao;

    @Inject
    public LocalDataSource(@NonNull UserDao userDao) {
        mUserDao = userDao;
    }
}
