package com.easyback.andriy.eazyback.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.easyback.andriy.eazyback.core.Core;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public abstract class GenericActivity extends Activity {

    private EzApplication mEzApplication;
    private SharedHelper mSharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEzApplication = (EzApplication) getApplication();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    protected void sendStat(String pScreenName) {
        EasyTracker.getInstance(this).set(Fields.SCREEN_NAME, pScreenName);
        EasyTracker.getInstance(this).send(MapBuilder.createAppView().build());
        EasyTracker.getInstance(this).activityStart(this);
    }

    protected SharedHelper getSharedHelper() {
        if (mSharedHelper == null) {
            mSharedHelper = mEzApplication.getSharedHelper();
        }
        return mSharedHelper;
    }

    protected Core getCore() {
        return mEzApplication.getCore();
    }

    protected void initBackButton() {
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void hideKeyboard(View pView) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                GenericActivity.this.finish();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }


}
