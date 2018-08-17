package com.cft.mamontov.imageprocessor.interactors;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface ImageInteractorHelper {

    Flowable<List<TransformedImage>> getOrderedImages();

    Completable insertImage(TransformedImage image);

    void setCurrentImage(String image);

    String getCurrentImage();
}
