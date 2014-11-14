package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;

public final class ExtraSettingsActivity extends GenericActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        initBackButton();

        mEditText = (EditText) findViewById(R.id.button_delay);
        mEditText.setText(String.valueOf(getSharedHelper().getButtonsDelayInSec()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSharedHelper().setButtonDelay(mEditText.getText().toString());
        hideKeyboard(mEditText);
    }
}
