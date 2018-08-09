package com.cft.mamontov.imageprocessor.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public final class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String mId;

    public User(@NonNull String id) {
        mId = id;
    }

    @NonNull
    public String getId() {
        return mId;
    }
}
