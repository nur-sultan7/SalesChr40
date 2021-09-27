package com.akhmadov.saleschr2018;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Akhmadov on 30.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "salesChrDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // Log.d( "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table favorites ("
                + "id integer primary key autoincrement,"
                + "object_id text,"
                + "shop_id text,"
                + "shop_name text,"
                + "tovar_opisanie text,"
                + "tovar_name text,"
                + "tovar_image text,"
                + "tovar_big_image text,"
                + "tovar_new_price text,"
                + "tovar_skidka text,"
                + "tovar_old_price text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}