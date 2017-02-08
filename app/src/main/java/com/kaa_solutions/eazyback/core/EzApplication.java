package com.kaa_solutions.eazyback.core;

import android.app.Application;

import com.kaa_solutions.eazyback.db.DelayContactDAO;

public final class EzApplication extends Application {

    private SharedHelper mSharedHelper;
    private DelayContactDAO contactDAO;
    private Core mCore;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedHelper = new SharedHelper(this);
        contactDAO = new DelayContactDAO(getApplicationContext());
        mCore = new Core(this, mSharedHelper, contactDAO);
    }

    public SharedHelper getSharedHelper() {
        return mSharedHelper;
    }

    public Core getCore() {
        return mCore;
    }

    public DelayContactDAO getContactDAO() {
        return contactDAO;
    }
}
