package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoadInteractor implements LoadInteractorHelper {

    private Repository mRepository;
    private SchedulerProviderHelper mScheduler;

    @Inject
    public LoadInteractor(Repository repository, SchedulerProviderHelper scheduler) {
        this.mRepository = repository;
        this.mScheduler = scheduler;
    }

    @Override
    public Observable<Response<ResponseBody>> getImageFromUrl(String url) {
        return mRepository.getImageFromUrl(url).
                subscribeOn(mScheduler.io());
    }
}
