package com.kaa_solutions.eazyback.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_DELAY_CONTACTS = "delay_contacts";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PHONE = "phone";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "eazyback_db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(getClass().getSimpleName(), "_onCreate();");
        db.execSQL("create table " + TABLE_DELAY_CONTACTS + "(" + COLUMN_ID + " integer primary key," + COLUMN_NAME + " text," + COLUMN_PHONE + " text unique" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(getClass().getSimpleName(), "_onUpdate();");
        db.execSQL("drop table if exists " + TABLE_DELAY_CONTACTS);
        onCreate(db);

    }
}
