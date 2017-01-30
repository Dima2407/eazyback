package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.easyback.andriy.eazyback.R;

public final class DeviceManagerActivity extends GenericActivity {

    private Switch mPlugAuto, mPlugManual, mPlugIgnore, mUnplugAuto, mUnplugManual, mUnplugIgnore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_devices);
        setContentView(R.layout.activity_devices);

        initBackButton();

        CompoundButton.OnCheckedChangeListener listener = new Checker();

        mPlugAuto = (Switch) findViewById(R.id.plug_event_automatic);
        mPlugAuto.setChecked(getSharedHelper().getPlugHeadsetAutomatControl());
        mPlugAuto.setOnCheckedChangeListener(listener);

        mPlugManual = (Switch) findViewById(R.id.plug_event_manual);
        mPlugManual.setChecked(getSharedHelper().getPlugHeadsetManualControl());
        mPlugManual.setOnCheckedChangeListener(listener);

        mPlugIgnore = (Switch) findViewById(R.id.plug_event_ignore);
        mPlugIgnore.setChecked(getSharedHelper().getPlugHeadsetIgnoreControl());
        mPlugIgnore.setOnCheckedChangeListener(listener);


        mUnplugAuto = (Switch) findViewById(R.id.un_plug_event_automatic);
        mUnplugAuto.setChecked(getSharedHelper().getUnPlugHeadsetAutomatControl());
        mUnplugAuto.setOnCheckedChangeListener(listener);

        mUnplugManual = (Switch) findViewById(R.id.un_plug_event_manual);
        mUnplugManual.setChecked(getSharedHelper().getUnPlugHeadsetManualControl());
        mUnplugManual.setOnCheckedChangeListener(listener);

        mUnplugIgnore = (Switch) findViewById(R.id.un_plug_event_ignore);
        mUnplugIgnore.setChecked(getSharedHelper().getUnPlugHeadsetIgnoreControl());
        mUnplugIgnore.setOnCheckedChangeListener(listener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
    }

    private final class Checker implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {

                case R.id.plug_event_automatic:
                    getSharedHelper().setPlugHeadsetAutomatControl(isChecked);
                    if (isChecked) {
                        mPlugIgnore.setChecked(false);
                    }
                    break;

                case R.id.plug_event_manual:
                    getSharedHelper().setPlugHeadsetManualControl(isChecked);
                    if (isChecked) {
                        mPlugIgnore.setChecked(false);
                    }
                    break;

                case R.id.plug_event_ignore:
                    getSharedHelper().setPlugHeadsetIgnoreControl(isChecked);
                    if (isChecked) {
                        mPlugAuto.setChecked(false);
                        mPlugManual.setChecked(false);
                    }
                    break;

                case R.id.un_plug_event_automatic:
                    getSharedHelper().setUnPlugHeadsetAutomatControl(isChecked);
                    if (isChecked) {
                        mUnplugIgnore.setChecked(false);
                    }
                    break;

                case R.id.un_plug_event_manual:
                    getSharedHelper().setUnPlugHeadsetManualControl(isChecked);
                    if (isChecked) {
                        mUnplugIgnore.setChecked(false);
                    }
                    break;

                case R.id.un_plug_event_ignore:
                    getSharedHelper().setUnPlugHeadsetIgnoreControl(isChecked);
                    if (isChecked) {
                        mUnplugAuto.setChecked(false);
                        mUnplugManual.setChecked(false);
                    }
                    break;

            }
        }
    }
}
