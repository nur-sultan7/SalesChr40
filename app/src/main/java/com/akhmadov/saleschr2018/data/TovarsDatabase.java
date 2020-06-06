package com.akhmadov.saleschr2018.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tovar.class, FavouriteTovar.class, FollowingShop.class}, version = 12, exportSchema = false)
public abstract class TovarsDatabase extends RoomDatabase {
    private static TovarsDatabase database;
    private static final String DB_NAME="tovars_db";
    private static final Object LOCK = new Object();

    static TovarsDatabase getInstance(Context context)
    {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, TovarsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }
    public abstract TovarsDao tovarsDao();
}
