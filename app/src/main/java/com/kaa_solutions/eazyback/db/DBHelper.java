package com.kaa_solutions.eazyback.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

final class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 41;
    private static final String DATABASE_NAME = "eazyback_db";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBQueries.CREATE_DELAY_CONTACTS);
        sqLiteDatabase.execSQL(DBQueries.CREATE_PHONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DBQueries.DROP_DELAY_CONTACTS);
        sqLiteDatabase.execSQL(DBQueries.DROP_PHONES);
        onCreate(sqLiteDatabase);
        Log.e(getClass().getSimpleName(), "Database was update");
    }

}
