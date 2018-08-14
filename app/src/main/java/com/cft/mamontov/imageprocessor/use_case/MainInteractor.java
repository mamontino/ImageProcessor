package com.cft.mamontov.imageprocessor.use_case;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

public final class MainInteractor implements IMainInteractor {

    private Repository mRepository;
    private BaseSchedulerProvider mScheduler;

    @Inject
    MainInteractor(Repository repository, BaseSchedulerProvider scheduler) {
        mRepository = repository;
        mScheduler = scheduler;
    }

    @Override
    public Single<Response<ResponseBody> > getImageFromUrl(String url){
        return mRepository.getImageFromUrl(url)
                .subscribeOn(mScheduler.io());
    }
}
