package com.kaa_solutions.eazyback;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kaa_solutions.eazyback.db.DBHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    @Test
    public void createDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        DBHelper helper = new DBHelper(context);
        final SQLiteDatabase wriDatabase = helper.getWritableDatabase();


    }
}
