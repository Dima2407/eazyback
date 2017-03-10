package com.kaa_solutions.eazyback.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.kaa_solutions.eazyback.R;

public final class MainSettingsActivity extends GenericActivity {

    public static final String FILTER = "need_refresh";

    private EditText mRejectDelay, mCallBackDelay;
    private Switch mCallbackActivatedSwitch, mManualActivatedSwitch, mDevicesActivatedSwitch;
    private BroadcastReceiver mBroadcastReceiver;
    private Button fullReset;

    private ImageView autoCallBack, buttons, devices, delayedCalls, numbers, extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineButtons();

        setOnClickListener();



       /* mRejectDelay = (EditText) findViewById(R.id.reject_delay);
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

        fullReset = (Button) findViewById(R.id.full_reset);
        fullReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullReset();
            }
        });
*/
    }


    private void defineButtons() {
        autoCallBack = (ImageView) findViewById(R.id.auto_call_back_image);
        buttons = (ImageView) findViewById(R.id.buttons_image);
        devices = (ImageView) findViewById(R.id.devices_image);
        delayedCalls = (ImageView) findViewById(R.id.delayed_calls_image);
        numbers = (ImageView) findViewById(R.id.numbers_image);
        extra = (ImageView) findViewById(R.id.extra_image);
    }

    private void setOnClickListener() {
        View.OnClickListener clickListener = new Clicker();
        autoCallBack.setOnClickListener(clickListener);
        buttons.setOnClickListener(clickListener);
        devices.setOnClickListener(clickListener);
        delayedCalls.setOnClickListener(clickListener);
        numbers.setOnClickListener(clickListener);
        extra.setOnClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.auto_call_back_image:
                    startActivity(new Intent(getBaseContext(), AutoCallBackActivity.class));
                    break;
                case R.id.buttons_image:
                    startActivity(new Intent(getBaseContext(), ButtonsActivity.class));
                    break;
                case R.id.devices_image:
                    //TODO: old deviceActivity
                    startActivity(new Intent(getBaseContext(), DeviceManagerActivity.class));
                    break;
                case R.id.delayed_calls_image:
                    //TODO: old delayActivity
                    startActivity(new Intent(getBaseContext(), DelayCallbackNumbersActivity.class));
                    break;
                case R.id.numbers_image:
                    //TODO: old numbersActivity
                    startActivity(new Intent(getBaseContext(), NumbersManagerActivity.class));
                    break;
                case R.id.extra_image:
                    //TODO: old ExtraActivity
                    startActivity(new Intent(getBaseContext(), ExtraSettingsActivity.class));
                    break;

            }
        }
    }

    /*

    public void fullReset() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.dialog_title);
        dialog.setMessage(R.string.dialog_message);
        dialog.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DelayContactDAO(getApplicationContext()).dropAndCreateTable();
                getSharedHelper().clearData();

                Intent i = getApplicationContext().getPackageManager()
                        .getLaunchIntentForPackage(getApplicationContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        dialog.setNegativeButton(R.string.cancel, null);
        dialog.create();
        dialog.show();
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

        if (Validator.validate(rejectTime)) {
            getSharedHelper().setRejectDelay(rejectTime);
            if (Validator.validate(callbackTime)) {
                getSharedHelper().setCallbackDelay(callbackTime);
                finish();
            } else {
                Toast toast = Toast.makeText(this, R.string.call_back_delay, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else {
            final Toast toast = Toast.makeText(this, R.string.time_reject_delay, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void setCheckedState() {
        mCallbackActivatedSwitch.setChecked(getSharedHelper().getIsCallbacksActivate());
        mManualActivatedSwitch.setChecked(getSharedHelper().getIsActivateManualMode());
        mDevicesActivatedSwitch.setChecked(getSharedHelper().getIsActivatedDeviceControl());
    }

    private void deviceCaseSwitcher() {
        if (getSharedHelper().getIsActivatedDeviceControl()) {
            ComponentLauncher.startDeviceService(getApplicationContext());

            mBroadcastReceiver = new RefreshReceiver();
            IntentFilter intentFilter = new IntentFilter(FILTER);
            registerReceiver(mBroadcastReceiver, intentFilter);
        } else {
            ComponentLauncher.stopDeviceService(getApplicationContext());
            mBroadcastReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.callback_activator:
                    getSharedHelper().setCallbackActivate(isChecked);
                    if (isChecked) {
                        getSharedHelper().setActivateManualMode(false);
                        mManualActivatedSwitch.setChecked(false);
                    }
                    break;

                case R.id.device_activator:
                    getSharedHelper().setDeviceActive(isChecked);
                    deviceCaseSwitcher();
                    break;

                case R.id.manual_activator:
                    getSharedHelper().setActivateManualMode(isChecked);
                    if (isChecked) {
                        getSharedHelper().setCallbackActivate(false);
                        mCallbackActivatedSwitch.setChecked(false);
                    }
                    break;
            }

        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_to_button_controls:
                    ComponentLauncher.launchCallPanelActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_phone_settings:
                    ComponentLauncher.launchNumbersActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_devices_control:
                    ComponentLauncher.launchDevicesActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_delay_callback_control:
                    ComponentLauncher.launchDelayBackActivity(MainSettingsActivity.this);
                    break;

                case R.id.main_to_extras_control:
                    ComponentLauncher.launchExtrasActivity(MainSettingsActivity.this);
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
*/


}
