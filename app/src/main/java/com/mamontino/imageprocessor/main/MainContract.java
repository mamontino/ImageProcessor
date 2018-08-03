package com.mamontino.imageprocessor.main;

import com.mamontino.imageprocessor.BasePresenter;
import com.mamontino.imageprocessor.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
