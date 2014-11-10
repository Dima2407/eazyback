package com.easyback.andriy.eazyback.ui.activitys;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.easyback.andriy.eazyback.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CallPanelSettingsActivity extends GenericActivity {

    private CheckBox mAcceptBox, mRejectBox, mCallbackBox, mCloseBox;
    private Map<Integer, Boolean> mButtonsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_panel);

        CompoundButton.OnCheckedChangeListener listener = new Checker();

        Switch manualModeSwitch = (Switch) findViewById(R.id.activator_manual_control);
        manualModeSwitch.setChecked(getSharedHelper().getIsActivateManualMode());
        manualModeSwitch.setOnCheckedChangeListener(listener);

        mAcceptBox = (CheckBox) findViewById(R.id.accept_button_check);
        mAcceptBox.setOnCheckedChangeListener(listener);

        mRejectBox = (CheckBox) findViewById(R.id.reject_button_check);
        mRejectBox.setOnCheckedChangeListener(listener);

        mCallbackBox = (CheckBox) findViewById(R.id.callback_button_check);
        mCallbackBox.setOnCheckedChangeListener(listener);

        mCloseBox = (CheckBox) findViewById(R.id.close_button_check);
        mCloseBox.setOnCheckedChangeListener(listener);

        List<Boolean> activatedButtons = getSharedHelper().getActivatedButtons();
        mButtonsStatus = new HashMap<Integer, Boolean>(activatedButtons.size());
        for (int i = 0; i < activatedButtons.size(); i++) {
            setActivateStatus(i, activatedButtons.get(i));
            mButtonsStatus.put(i, activatedButtons.get(i));
        }

        initBackButton();
    }


    private void setActivateStatus(int pIndex, boolean pStatus) {
        switch (pIndex) {
            case 0:
                mAcceptBox.setChecked(pStatus);
                break;

            case 1:
                mRejectBox.setChecked(pStatus);
                break;

            case 2:
                mCallbackBox.setChecked(pStatus);
                break;

            case 3:
                mCloseBox.setChecked(pStatus);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStat(getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSharedHelper().setActivatedButtons(new ArrayList<Boolean>(mButtonsStatus.values()));

    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {

                case R.id.activator_manual_control:
                    getSharedHelper().setActivateManualMode(isChecked);
                    break;

                case R.id.accept_button_check:
                    mButtonsStatus.put(0, isChecked);
                    break;

                case R.id.reject_button_check:
                    mButtonsStatus.put(1, isChecked);
                    break;

                case R.id.callback_button_check:
                    mButtonsStatus.put(2, isChecked);
                    break;

                case R.id.close_button_check:
                    mButtonsStatus.put(3, isChecked);
                    break;
            }
        }
    }
}
