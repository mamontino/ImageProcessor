package com.cft.mamontov.imageprocessor.data.db;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface DatabaseHelper {

    Flowable<List<TransformedImage>> getOrderedImages();

    Completable insertImage(TransformedImage image);
}
