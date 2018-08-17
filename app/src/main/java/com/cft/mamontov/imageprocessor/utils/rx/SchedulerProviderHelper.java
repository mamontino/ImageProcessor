package com.cft.mamontov.imageprocessor.utils.rx;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface SchedulerProviderHelper {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    Scheduler newThread();
}
