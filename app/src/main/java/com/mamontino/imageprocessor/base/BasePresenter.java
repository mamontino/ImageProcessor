package com.mamontino.imageprocessor.base;

public interface BasePresenter<T> {

    void initView(T view);

    void destroyView();
}
