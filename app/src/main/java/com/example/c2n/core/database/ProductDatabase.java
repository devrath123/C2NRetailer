package com.example.c2n.core.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.c2n.core.models.CartProductDataModel;

@Database(entities = {CartProductDataModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProductDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
