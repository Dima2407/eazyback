package com.easyback.andriy.eazyback.ui.activitys;

import android.os.Bundle;

public final class DeviceManagerActivity extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStat(getClass().getSimpleName());
    }
}
