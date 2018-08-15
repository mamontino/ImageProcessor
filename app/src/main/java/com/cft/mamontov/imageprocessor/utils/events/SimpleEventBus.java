package com.cft.mamontov.imageprocessor.utils.events;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SimpleEventBus {

    private final PublishSubject<Object> mBusSubject;

    public SimpleEventBus() {
        mBusSubject = PublishSubject.create();
    }

    public void post(Object event) {
        mBusSubject.onNext(event);
    }

    public Observable<Object> observable() {
        return mBusSubject;
    }

    public <T> Observable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object event) {
                return eventClass.isInstance(event);
            }
        }).map(new Func1<Object, T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T call(Object event) {
                return (T) event;
            }
        });
    }
}