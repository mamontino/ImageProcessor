package com.cft.mamontov.imageprocessor.data.db;

import android.support.annotation.NonNull;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class DatabaseManager implements DatabaseHelper {

    private final ImageDao mImageDao;

    @Inject
    public DatabaseManager(@NonNull ImageDao imageDao) {
        mImageDao = imageDao;
    }

    @Override
    public Flowable<List<TransformedImage>> getOrderedImages() {
        return mImageDao.getOrderedImages();
    }

    @Override
    public Completable insertImage(TransformedImage image) {
        return Completable.fromAction(() -> mImageDao.insertImage(image));
    }
}
