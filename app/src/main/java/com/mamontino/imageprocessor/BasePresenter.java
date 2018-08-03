package com.mamontino.imageprocessor;

public interface BasePresenter<T> {

    void initView(T view);

    void destroyView();
}
