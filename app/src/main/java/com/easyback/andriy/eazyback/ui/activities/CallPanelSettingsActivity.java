package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ComponentLauncher;

public final class CallPanelSettingsActivity extends GenericActivity {

    private CheckBox mAcceptBox, mRejectBox, mCallbackBox, mDelayCallbackBox;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_panel);

        CompoundButton.OnCheckedChangeListener listener = new Checker();

        mAcceptBox = (CheckBox) findViewById(R.id.accept_button_check);
        mAcceptBox.setChecked(getSharedHelper().getActivateAcceptButton());
        mAcceptBox.setOnCheckedChangeListener(listener);


        mRejectBox = (CheckBox) findViewById(R.id.reject_button_check);
        mRejectBox.setChecked(getSharedHelper().getActivateRejectButton());
        mRejectBox.setOnCheckedChangeListener(listener);

        mCallbackBox = (CheckBox) findViewById(R.id.callback_button_check);
        mCallbackBox.setChecked(getSharedHelper().getActivateCallbackButton());
        mCallbackBox.setOnCheckedChangeListener(listener);

        mDelayCallbackBox = (CheckBox) findViewById(R.id.delay_callback_button_check);
        mDelayCallbackBox.setChecked(getSharedHelper().getActivateDelayCallbackButton());
        mDelayCallbackBox.setOnCheckedChangeListener(listener);

        mRadioGroup = (RadioGroup) findViewById(R.id.button_mode_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioListener());

        findViewById(R.id.float_window_settings).setOnClickListener(new Clicker());

        initBackButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
        if (getSharedHelper().getManualInterceptMode()) {
            mRadioGroup.check(R.id.mod_only_list);
        } else {
            mRadioGroup.check(R.id.mod_total_intercept);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {

                case R.id.accept_button_check:
                    getSharedHelper().setActivateAcceptButton(isChecked);
                    break;

                case R.id.reject_button_check:
                    getSharedHelper().setActivateRejectButton(isChecked);
                    break;

                case R.id.callback_button_check:
                    getSharedHelper().setActivateCallbackButton(isChecked);
                    break;

                case R.id.delay_callback_button_check:
                    getSharedHelper().setActivateDelayCallbackButton(isChecked);
                    break;
            }
        }
    }

    private final class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {

                case R.id.mod_only_list:
                    getSharedHelper().setManualInterceptMode(true);
                    break;

                case R.id.mod_total_intercept:
                    getSharedHelper().setManualInterceptMode(false);
                    break;
            }
        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.float_window_settings:
                    ComponentLauncher.launchFloatSettingsActivity(CallPanelSettingsActivity.this);
                    break;
            }
        }
    }
}
