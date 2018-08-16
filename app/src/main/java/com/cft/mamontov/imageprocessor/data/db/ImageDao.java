package com.cft.mamontov.imageprocessor.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImageDao {

    @Query("select * from image order by id desc limit 20 ")
    Flowable<List<TransformedImage>> getOrderedImages();

    @Insert(onConflict = REPLACE)
    void insertImage(TransformedImage image);
}
