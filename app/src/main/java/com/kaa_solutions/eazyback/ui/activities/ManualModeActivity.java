package com.kaa_solutions.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;

public class ManualModeActivity extends GenericActivity {

    private RelativeLayout layoutManualModeSettings;
    private Switch mManualActivatedSwitch;
    private CheckBox mAcceptBox, mRejectBox, mCallbackBox, mDelayCallbackBox;
    private RadioGroup mRadioGroup;
    private RadioButton mod_only_list, mod_total_intercept;
    private RelativeLayout layoutChoosePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_button_control);
        setIsManualModeActive();
        setOnCheckedChanged();

    }

    private void setOnCheckedChanged() {
        mManualActivatedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedHelper().setActivateManualMode(isChecked);
                if (getSharedHelper().getIsCallbacksActivate()) {
                    getSharedHelper().setCallbackActivate(false);
                    Toast.makeText(getApplicationContext(), R.string.message_disableCallBackMode, Toast.LENGTH_SHORT).show();
                }
                if (isChecked) {
                    layoutManualModeSettings = (RelativeLayout) findViewById(R.id.manual_mode_settings);
                    layoutManualModeSettings.setVisibility(View.VISIBLE);
                    loadValuesFromPreferences();
                } else {
                    layoutManualModeSettings = (RelativeLayout) findViewById(R.id.manual_mode_settings);
                    layoutManualModeSettings.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setIsManualModeActive() {
        mManualActivatedSwitch = (Switch) findViewById(R.id.manual_activator);
        if (getSharedHelper().getIsActivateManualMode()) {
            mManualActivatedSwitch.setChecked(true);
            loadValuesFromPreferences();
        } else {
            layoutManualModeSettings = (RelativeLayout) findViewById(R.id.manual_mode_settings);
            layoutManualModeSettings.setVisibility(View.INVISIBLE);
        }
    }

    private void loadValuesFromPreferences() {
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

        mod_only_list = (RadioButton) findViewById(R.id.mod_only_list);
        mod_only_list.setChecked(getSharedHelper().getManualInterceptMode());

        mod_total_intercept = (RadioButton) findViewById(R.id.mod_total_intercept);
        mod_total_intercept.setChecked(!getSharedHelper().getManualInterceptMode());

        layoutChoosePosition = (RelativeLayout) findViewById(R.id.layout_options_choose_position);
        layoutChoosePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getSimpleName(), "onClick: layout_options_choose_position");
                startActivity(new Intent(getApplicationContext(), FloatWindowSettings.class));
            }
        });

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
}
