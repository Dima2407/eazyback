package com.kaa_solutions.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.utils.ViewUtils;

import java.util.List;

public final class NumbersManagerActivity extends GenericActivity {

    private List<EditText> mTelephoneCells;
    private Button openAddressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_numbers);
        switch (getSharedHelper().getDonate()) {
            case -1:
                setContentView(R.layout.activity_numbers);
                mTelephoneCells = ViewUtils.initPhoneSells(this, getSharedHelper().getTargetNumbers());
                break;

            case 1:
                setContentView(R.layout.activity_numbers_extended);
                mTelephoneCells = ViewUtils.initPhoneSellsExtended(this, getSharedHelper().getTargetNumbers());
                break;

            case 2:
                setContentView(R.layout.activity_numbers_ultra);
                mTelephoneCells = ViewUtils.initPhoneSellsUltra(this, getSharedHelper().getTargetNumbers());
                break;
        }

        openAddressBook = (Button) findViewById(R.id.open_address_Book);
        openAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(NumbersManagerActivity.this, PhoneBookActivity.class);
                startActivity(Intent);
            }
        });

        initBackButton();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewUtils.savePhoneCells(mTelephoneCells, getSharedHelper());
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainSettingsActivity.class));
    }


}
