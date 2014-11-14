package com.easyback.andriy.eazyback.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ComponentLaunchControl;
import com.easyback.andriy.eazyback.utils.Validator;

public final class MainSettingsActivity extends GenericActivity {

    public static final String FILTER = "need_refresh";

    private EditText mRejectDelay, mCallBackDelay;
    private Switch mCallbackActivatedSwitch, mManualActivatedSwitch, mDevicesActivatedSwitch;
    private BroadcastReceiver mBroadcastReceiver;

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

        mCallbackActivatedSwitch = (Switch) findViewById(R.id.callback_activator);
        mCallbackActivatedSwitch.setOnCheckedChangeListener(checkListener);

        mDevicesActivatedSwitch = (Switch) findViewById(R.id.device_activator);
        mDevicesActivatedSwitch.setOnCheckedChangeListener(checkListener);

        mManualActivatedSwitch = (Switch) findViewById(R.id.manual_activator);
        mManualActivatedSwitch.setOnCheckedChangeListener(checkListener);

        View.OnClickListener clickListener = new Clicker();
        findViewById(R.id.main_to_button_controls).setOnClickListener(clickListener);
        findViewById(R.id.main_to_phone_settings).setOnClickListener(clickListener);
        findViewById(R.id.main_to_devices_control).setOnClickListener(clickListener);
        findViewById(R.id.main_to_delay_callback_control).setOnClickListener(clickListener);
        findViewById(R.id.main_to_extras_control).setOnClickListener(clickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());

        setCheckedState();
        deviceCaseSwitcher();
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

        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    private void makeSaveProcedure() {
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

    private void setCheckedState() {
        mCallbackActivatedSwitch.setChecked(getSharedHelper().getIsCallbacksActivate());
        mManualActivatedSwitch.setChecked(getSharedHelper().getIsActivateManualMode());
        mDevicesActivatedSwitch.setChecked(getSharedHelper().getIsActivatedDeviceControl());
    }

    private void deviceCaseSwitcher() {
        if (getSharedHelper().getIsActivatedDeviceControl()) {
            ComponentLaunchControl.startDeviceService(getApplicationContext());

            mBroadcastReceiver = new RefreshReceiver();
            IntentFilter intentFilter = new IntentFilter(FILTER);
            registerReceiver(mBroadcastReceiver, intentFilter);
        } else {
            mBroadcastReceiver = null;
        }
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.callback_activator:
                    //getSharedHelper().setCallbackActivate(isChecked);
                    getCore().makeParse("111");
                    break;

                case R.id.device_activator:
                    getSharedHelper().setDeviceActive(isChecked);

                    if (isChecked) {
                        ComponentLaunchControl.startDeviceService(getApplication());
                    } else {
                        ComponentLaunchControl.stopDeviceService(getApplication());
                    }
                    break;

                case R.id.manual_activator:
                    getSharedHelper().setActivateManualMode(isChecked);
                    break;
            }

        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_to_button_controls:
                    ComponentLaunchControl.launchCallPanelActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_phone_settings:
                    ComponentLaunchControl.launchNumbersActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_devices_control:
                    ComponentLaunchControl.launchDevicesActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_delay_callback_control:
                    ComponentLaunchControl.launchDelayBackActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_extras_control:
                    ComponentLaunchControl.launchExtrasActivity(MainSettingsActivity.this);
                    break;
            }
        }
    }

    private final class RefreshReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setCheckedState();
        }
    }
}
