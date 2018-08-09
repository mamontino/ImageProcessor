package com.cft.mamontov.imageprocessor.mvp.main;

import android.graphics.Bitmap;

import com.cft.mamontov.imageprocessor.base.BasePresenter;
import com.cft.mamontov.imageprocessor.base.BaseView;
import com.cft.mamontov.imageprocessor.source.models.TransformedImage;
import com.cft.mamontov.imageprocessor.utils.tranformation.Transformation;

import java.net.URI;
import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showProgressIndicator(boolean b);
        void addItems(List<TransformedImage> list);
        void addItem(TransformedImage picture);
        void updateProcessing(int val);
    }

    interface Presenter extends BasePresenter<View> {
        void getImageFromUrl(URI url);
        void loadImageList();
        void transformImage(Bitmap bitmap, Transformation transformation);
    }
}
