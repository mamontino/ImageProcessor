package com.mamontino.imageprocessor.mvp.main;

import com.mamontino.imageprocessor.base.BasePresenter;
import com.mamontino.imageprocessor.base.BaseView;

import java.net.URI;

public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<View> {
        void getImageFromUrl(URI url);
    }
}
