package com.kaa_solutions.eazyback.core;

import android.app.Application;

import com.kaa_solutions.eazyback.db.DelayContactDAO;
import com.kaa_solutions.eazyback.db.PhonesDAO;

public final class EzApplication extends Application {
    public static String TAG;
    private SharedHelper mSharedHelper;
    private DelayContactDAO delayedContactDAO;
    private PhonesDAO phonesDAO;
    private Core mCore;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedHelper = new SharedHelper(this);
        delayedContactDAO = new DelayContactDAO(getApplicationContext());
        phonesDAO = new PhonesDAO(getApplicationContext());
        mCore = new Core(this, mSharedHelper, delayedContactDAO, phonesDAO);

    }

    public SharedHelper getSharedHelper() {
        return mSharedHelper;
    }

    public Core getCore() {
        return mCore;
    }

    public DelayContactDAO getDelayedContactDAO() {
        return delayedContactDAO;
    }

    public PhonesDAO getPhonesDAO() {
        return phonesDAO;
    }
}
