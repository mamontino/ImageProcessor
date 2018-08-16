package com.cft.mamontov.imageprocessor.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cft.mamontov.imageprocessor.data.models.TransformedImage;

@Database(entities = {TransformedImage.class}, version = 1)
public abstract class IPDatabase extends RoomDatabase {

    public abstract ImageDao userDao();
}
