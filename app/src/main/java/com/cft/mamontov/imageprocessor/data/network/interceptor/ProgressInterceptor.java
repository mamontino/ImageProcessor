package com.cft.mamontov.imageprocessor.data.network.interceptor;

import com.cft.mamontov.imageprocessor.utils.events.ProgressEvent;
import com.cft.mamontov.imageprocessor.utils.events.ProgressResponseBody;
import com.cft.mamontov.imageprocessor.utils.events.SimpleEventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ProgressInterceptor implements Interceptor {

    public static final String DOWNLOAD_IDENTIFIER_HEADER = "download-identifier";

    private final SimpleEventBus eventBus;

    public ProgressInterceptor(SimpleEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Response.Builder builder = originalResponse.newBuilder();

        String downloadIdentifier = originalResponse.request().header(DOWNLOAD_IDENTIFIER_HEADER);
        boolean isAStream = originalResponse.header("content-type", "")
                .equals("application/octet-stream");
        boolean fileIdentifierIsSet = downloadIdentifier != null && !downloadIdentifier.isEmpty();

        if(isAStream && fileIdentifierIsSet) {
            builder.body(new ProgressResponseBody(downloadIdentifier, originalResponse.body(),
                    (identifier, bytesRead, contentLength, done) -> {
                eventBus.post(new ProgressEvent(identifier, contentLength, bytesRead));
            }));
        } else {
            builder.body(originalResponse.body());
        }

        return builder.build();
    }
}