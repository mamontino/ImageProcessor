package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.utils.rx.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoadInteractor implements ILoadInteractor {

    private Repository mRepository;
    private BaseSchedulerProvider mScheduler;

    @Inject
    public LoadInteractor(Repository repository, BaseSchedulerProvider scheduler) {
        this.mRepository = repository;
        this.mScheduler = scheduler;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mRepository.getImageFromUrl(url).
                subscribeOn(mScheduler.io());
    }
}
