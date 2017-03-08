package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;

import com.kaa_solutions.eazyback.R;

public final class AutoCallBackActivity extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_call_back);

        initBackButton();
    }
}
