package com.mamontino.imageprocessor.source.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class IPDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
