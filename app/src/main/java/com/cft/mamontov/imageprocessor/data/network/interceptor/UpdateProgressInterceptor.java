package com.cft.mamontov.imageprocessor.data.network.interceptor;

import android.support.annotation.NonNull;

import com.cft.mamontov.imageprocessor.utils.events.ProgressEvent;
import com.cft.mamontov.imageprocessor.utils.ProgressListener;
import com.cft.mamontov.imageprocessor.data.models.ProgressResponseBody;
import com.cft.mamontov.imageprocessor.utils.events.RxBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UpdateProgressInterceptor implements Interceptor{

    private RxBus mRxBus;

    public UpdateProgressInterceptor(RxBus rxBus) {
        mRxBus = rxBus;
    }

    private final ProgressListener progressListener = new ProgressListener() {
        boolean firstUpdate = true;

        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            if (done) {
                if (mRxBus.hasObservers()){
                    mRxBus.send(new ProgressEvent("completed", 100,
                            false, false));
                }
                System.out.println("completed");
            } else {
                if (firstUpdate) {
                    firstUpdate = false;
                    if (contentLength == -1) {
                        if (mRxBus.hasObservers()){
                            mRxBus.send(new ProgressEvent("content-length: unknown",
                                    100, false, true));
                        }
                        System.out.println("content-length: unknown");
                    }
                }

                System.out.println(bytesRead);

                if (contentLength != -1) {
                    if (mRxBus.hasObservers()){
                        mRxBus.send(new ProgressEvent("update",
                                ((100 * bytesRead) / contentLength), true, false));
                    }
                    System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
                }
            }
        }
    };

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
    }
}

