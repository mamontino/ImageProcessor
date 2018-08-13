package com.cft.mamontov.imageprocessor.data;

import com.cft.mamontov.imageprocessor.di.name.Local;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository implements DataSource {

    private final DataSource mLocalDataSource;

    @Inject
    Repository(@Local DataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }
}
