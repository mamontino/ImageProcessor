package com.mamontino.imageprocessor.mvp.main;

import android.graphics.Bitmap;

import com.mamontino.imageprocessor.base.BasePresenter;
import com.mamontino.imageprocessor.base.BaseView;
import com.mamontino.imageprocessor.source.models.TransformedImage;
import com.mamontino.imageprocessor.utils.tranformation.Transformation;

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
