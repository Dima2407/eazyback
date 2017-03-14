package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.widget.Switch;

import com.kaa_solutions.eazyback.R;

public class ManualModeActivity extends GenericActivity {

    private Switch mCallbackActivatedSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);

        initBackButton();
    }
}
