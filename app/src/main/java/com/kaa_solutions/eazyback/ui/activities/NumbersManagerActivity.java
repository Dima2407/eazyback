package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaa_solutions.eazyback.R;

import java.util.List;

public final class NumbersManagerActivity extends GenericActivity {

    private List<EditText> mTelephoneCells;
    private Button openAddressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_numbers);
        setContentView(R.layout.activity_number);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
      /*  openAddressBook = (Button) findViewById(R.id.open_address_Book);
        openAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(NumbersManagerActivity.this, PhoneBookActivity.class);
                startActivity(Intent);
            }
        });
*/


//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        ViewUtils.savePhoneCells(mTelephoneCells, getSharedHelper());
    }


}
