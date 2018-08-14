package com.cft.mamontov.imageprocessor.data.db;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class LocalDataSource implements ILocalDataSource {

    private final UserDao mUserDao;

    @Inject
    public LocalDataSource(@NonNull UserDao userDao) {
        mUserDao = userDao;
    }
}
