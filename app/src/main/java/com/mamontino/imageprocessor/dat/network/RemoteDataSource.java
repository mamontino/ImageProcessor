package com.mamontino.imageprocessor.dat.network;

import com.mamontino.imageprocessor.dat.DataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteDataSource implements DataSource {

    @Inject
    public RemoteDataSource() {}
}
