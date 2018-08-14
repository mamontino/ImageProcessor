package com.cft.mamontov.imageprocessor.data.network;

import com.cft.mamontov.imageprocessor.data.DataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteDataSource implements DataSource {

    private final ApiService mApiService;

    @Inject
    RemoteDataSource(ApiService apiService) {
        this.mApiService = apiService;
    }
}
