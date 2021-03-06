package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kaa_solutions.eazyback.R;

public final class ExtraSettingsActivity extends GenericActivity {

    private EditText mEditText;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_extras);

        mEditText = (EditText) findViewById(R.id.button_delay);
        mEditText.setText(String.valueOf(getSharedHelper().getButtonsDelayInSec()));

        mCheckBox = (CheckBox) findViewById(R.id.button_auto_hide_call_panel);
        mCheckBox.setChecked(getSharedHelper().getAutoHideCallPanel());
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSharedHelper().setButtonDelay(mEditText.getText().toString());
        getSharedHelper().setAutoHideCallPanel(mCheckBox.isChecked());
        hideKeyboard(mEditText);
    }
}
