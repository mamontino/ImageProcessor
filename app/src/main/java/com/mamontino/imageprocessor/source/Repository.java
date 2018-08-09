package com.mamontino.imageprocessor.source;

import com.mamontino.imageprocessor.di.name.Local;
import com.mamontino.imageprocessor.di.name.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository implements DataSource {

    private final DataSource mRemoteDataSource;

    private final DataSource mLocalDataSource;

    @Inject
    Repository(@Remote DataSource remoteDataSource,
               @Local DataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }
}
