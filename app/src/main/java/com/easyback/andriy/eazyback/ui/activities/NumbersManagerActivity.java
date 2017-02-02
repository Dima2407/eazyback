package com.easyback.andriy.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.utils.ViewUtils;

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

                Intent Intent = new Intent(view.getContext(), PhoneBookActivity.class);
                view.getContext().startActivity(Intent);

            }
        });

        initBackButton();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
        hideKeyboard(mTelephoneCells.get(0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewUtils.savePhoneCells(mTelephoneCells, getSharedHelper());
        hideKeyboard(mTelephoneCells.get(0));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                startActivity(new Intent(getBaseContext(), MainSettingsActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainSettingsActivity.class));
    }


}
