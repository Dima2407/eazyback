package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ViewUtils;

import java.util.List;

public final class NumbersManagerActivity extends GenericActivity {

    private List<EditText> mTelephoneCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        mTelephoneCells = ViewUtils.initPhoneSells(this, getSharedHelper().getTargetNumbers());
        initBackButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStat(getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewUtils.savePhoneCells(mTelephoneCells, getSharedHelper());
        hideKeyboard(mTelephoneCells.get(0));
    }
}
