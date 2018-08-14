package com.cft.mamontov.imageprocessor.data.network.interceptors;

import android.support.annotation.NonNull;

import com.cft.mamontov.imageprocessor.data.network.NetworkChecker;
import com.cft.mamontov.imageprocessor.exceptions.NoNetworkException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkStateInterceptor implements Interceptor {

    private NetworkChecker networkChecker;

    public NetworkStateInterceptor(NetworkChecker networkChecker) {
        this.networkChecker = networkChecker;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (!networkChecker.isConnected()) {
            throw new NoNetworkException("No network connection");
        }

        try {
            return chain.proceed(requestBuilder.build());
        } catch (SocketTimeoutException | UnknownHostException e) {
            throw new NoNetworkException();
        }
    }
}
