package com.easyback.andriy.eazyback.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;
import com.easyback.andriy.eazyback.utils.Validator;
import com.easyback.andriy.eazyback.utils.ViewInitUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.List;

public class SettingsActivity extends Activity {

    private List<EditText> mTelephoneCells;
    private EditText mRejectDelay, mCallBackDelay;
    private Switch mActivatedSwitch;
    private EzApplication mEzApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mEzApplication = (EzApplication) getApplication();

        SharedHelper sharedHelper = mEzApplication.getSharedHelper();

        mTelephoneCells = ViewInitUtils.initPhoneSells(this, sharedHelper.getTargetNumbers());

        mRejectDelay = (EditText) findViewById(R.id.reject_delay);
        if (sharedHelper.getRejectDelay() != -1) {
            mRejectDelay.setText(String.valueOf(sharedHelper.getRejectDelay()));
        }

        mCallBackDelay = (EditText) findViewById(R.id.call_back_delay);

        if (sharedHelper.getCallbackDelay() != -1) {
            mCallBackDelay.setText(String.valueOf(sharedHelper.getCallbackDelay()));
        }

        mActivatedSwitch = (Switch) findViewById(R.id.activator);
        mActivatedSwitch.setChecked(sharedHelper.getIsActivate());
        mActivatedSwitch.setOnCheckedChangeListener(new Checker());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).set(Fields.SCREEN_NAME, getClass().getSimpleName());
        EasyTracker.getInstance(this).send(MapBuilder.createAppView().build());

        EasyTracker.getInstance(this).activityStart(this);
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

        String rejectTime = mRejectDelay.getText().toString();
        String callbackTime = mCallBackDelay.getText().toString();

        if (Validator.validate(rejectTime) && Validator.validate(callbackTime)) {
            sharedHelper.setRejectDelay(rejectTime);
            sharedHelper.setCallbackDelay(callbackTime);
            ViewInitUtils.savePhoneCells(mTelephoneCells, sharedHelper);
            finish();
        } else {
            Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mEzApplication.getSharedHelper().setActivate(isChecked);
        }
    }
}
