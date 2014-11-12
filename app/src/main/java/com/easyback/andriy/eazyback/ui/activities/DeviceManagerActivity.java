package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ComponentLaunchControl;

public final class DeviceManagerActivity extends GenericActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        initBackButton();

        CompoundButton.OnCheckedChangeListener listener = new Checker();

        Switch aSwitch;


        aSwitch = (Switch) findViewById(R.id.plug_event_automatic);
        aSwitch.setChecked(getSharedHelper().getPlugHeadsetAutomatControl());
        aSwitch.setOnCheckedChangeListener(listener);

        aSwitch = (Switch) findViewById(R.id.plug_event_manual);
        aSwitch.setChecked(getSharedHelper().getPlugHeadsetManualControl());
        aSwitch.setOnCheckedChangeListener(listener);

        aSwitch = (Switch) findViewById(R.id.plug_event_ignore);
        aSwitch.setChecked(getSharedHelper().getPlugHeadsetIgnoreControl());
        aSwitch.setOnCheckedChangeListener(listener);


        aSwitch = (Switch) findViewById(R.id.un_plug_event_automatic);
        aSwitch.setChecked(getSharedHelper().getUnPlugHeadsetAutomatControl());
        aSwitch.setOnCheckedChangeListener(listener);

        aSwitch = (Switch) findViewById(R.id.un_plug_event_manual);
        aSwitch.setChecked(getSharedHelper().getUnPlugHeadsetManualControl());
        aSwitch.setOnCheckedChangeListener(listener);

        aSwitch = (Switch) findViewById(R.id.un_plug_event_ignore);
        aSwitch.setChecked(getSharedHelper().getUnPlugHeadsetIgnoreControl());
        aSwitch.setOnCheckedChangeListener(listener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStat(getClass().getSimpleName());
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {

                case R.id.plug_event_automatic:
                    getSharedHelper().setPlugHeadsetAutomatControl(isChecked);
                    break;

                case R.id.plug_event_manual:
                    getSharedHelper().setPlugHeadsetManualControl(isChecked);
                    break;

                case R.id.plug_event_ignore:
                    getSharedHelper().setPlugHeadsetIgnoreControl(isChecked);
                    break;

                case R.id.un_plug_event_automatic:
                    getSharedHelper().setUnPlugHeadsetAutomatControl(isChecked);
                    break;

                case R.id.un_plug_event_manual:
                    getSharedHelper().setUnPlugHeadsetManualControl(isChecked);
                    break;

                case R.id.un_plug_event_ignore:
                    getSharedHelper().setUnPlugHeadsetIgnoreControl(isChecked);
                    break;

            }
        }
    }
}
