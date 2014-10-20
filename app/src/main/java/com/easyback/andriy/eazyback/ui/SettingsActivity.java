package com.easyback.andriy.eazyback.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;


public class SettingsActivity extends Activity {

    private EditText mTelephone, mRejectDelay, mCallBackDelay;
    private EzApplication mEzApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mEzApplication = (EzApplication) getApplication();

        SharedHelper sharedHelper = mEzApplication.getSharedHelper();

        mTelephone = (EditText) findViewById(R.id.number);
        mTelephone.setText(sharedHelper.getTargetPhone());

        mRejectDelay = (EditText) findViewById(R.id.reject_delay);
        mRejectDelay.setText(String.valueOf(sharedHelper.getRejectDelay()));

        mCallBackDelay = (EditText) findViewById(R.id.call_back_delay);
        mCallBackDelay.setText(String.valueOf(sharedHelper.getCallbackDelay()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                makeSaveProcedure();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeSaveProcedure() {
        SharedHelper sharedHelper = mEzApplication.getSharedHelper();


        finish();
    }
}
