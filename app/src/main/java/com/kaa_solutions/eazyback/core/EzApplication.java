package com.kaa_solutions.eazyback.core;

import android.app.Application;

public final class EzApplication extends Application {

    private SharedHelper mSharedHelper;
    private Core mCore;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedHelper = new SharedHelper(this);
        mCore = new Core(this, mSharedHelper);
    }

    public SharedHelper getSharedHelper() {
        return mSharedHelper;
    }

    public Core getCore() {
        return mCore;
    }
}
