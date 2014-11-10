package com.easyback.andriy.eazyback.ui.activitys;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ActivityLauncher;
import com.easyback.andriy.eazyback.utils.Validator;

public final class MainSettingsActivity extends GenericActivity {

    private EditText mRejectDelay, mCallBackDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mRejectDelay = (EditText) findViewById(R.id.reject_delay);
        if (getSharedHelper().getRejectDelayInSec() != -1) {
            mRejectDelay.setText(String.valueOf(getSharedHelper().getRejectDelayInSec()));
        }

        mCallBackDelay = (EditText) findViewById(R.id.call_back_delay);

        if (getSharedHelper().getCallbackDelayInSec() != -1) {
            mCallBackDelay.setText(String.valueOf(getSharedHelper().getCallbackDelayInSec()));
        }

        CompoundButton.OnCheckedChangeListener checkListener = new Checker();

        Switch ActivatedSwitch = (Switch) findViewById(R.id.activator);
        ActivatedSwitch.setChecked(getSharedHelper().getIsActivate());
        ActivatedSwitch.setOnCheckedChangeListener(checkListener);


        View.OnClickListener clickListener = new Clicker();
        findViewById(R.id.main_to_button_controls).setOnClickListener(clickListener);
        findViewById(R.id.main_to_phone_settings).setOnClickListener(clickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStat(getClass().getSimpleName());
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

    @Override
    protected void onStop() {
        super.onStop();
        hideKeyboard(mRejectDelay);
    }

    public void makeSaveProcedure() {
        String rejectTime = mRejectDelay.getText().toString();
        String callbackTime = mCallBackDelay.getText().toString();

        if (Validator.validate(rejectTime) && Validator.validate(callbackTime)) {
            getSharedHelper().setRejectDelay(rejectTime);
            getSharedHelper().setCallbackDelay(callbackTime);
            finish();
        } else {
            Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT).show();
        }
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.activator:
                    getSharedHelper().setActivate(isChecked);
                    break;
            }

        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_to_button_controls:
                    ActivityLauncher.launchCallPanelActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_phone_settings:
                    ActivityLauncher.launchNumbersActivity(MainSettingsActivity.this);
                    break;
            }
        }
    }
}
