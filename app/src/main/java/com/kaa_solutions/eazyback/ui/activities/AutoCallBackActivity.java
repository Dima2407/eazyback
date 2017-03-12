package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;

public final class AutoCallBackActivity extends GenericActivity {

    private Switch mCallbackActivatedSwitch;
    private EditText mRejectDelay, mCallBackDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_call_back);
        setStatusAutoCallbackControl();
        setOnClickListener();
        initBackButton();

    }

    private void setFieldValues() {
        mRejectDelay = (EditText) findViewById(R.id.reject_delay);
        mRejectDelay.setText(String.valueOf(getSharedHelper().getRejectDelayInSec()));
        mRejectDelay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    getSharedHelper().setRejectDelay(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mCallBackDelay = (EditText) findViewById(R.id.call_back_delay);
        mCallBackDelay.setText(String.valueOf(getSharedHelper().getCallbackDelayInSec()));
        mCallBackDelay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    getSharedHelper().setCallbackDelay(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setOnClickListener() {
        mCallbackActivatedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedHelper().setCallbackActivate(isChecked);
                if (getSharedHelper().getIsActivateManualMode()) {
                    getSharedHelper().setActivateManualMode(false);
                    Toast.makeText(getApplicationContext(), R.string.message_disableManualMode, Toast.LENGTH_SHORT).show();
                }
                if (isChecked) {
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.auto_call_back_settings);
                    relativeLayout.setVisibility(View.VISIBLE);
                    setFieldValues();
                } else {
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.auto_call_back_settings);
                    relativeLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setStatusAutoCallbackControl() {
        mCallbackActivatedSwitch = (Switch) findViewById(R.id.callback_activator);
        if (getSharedHelper().getIsCallbacksActivate()) {
            mCallbackActivatedSwitch.setChecked(true);
        } else {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.auto_call_back_settings);
            relativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
