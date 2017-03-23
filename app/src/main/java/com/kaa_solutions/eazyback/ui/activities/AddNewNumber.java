package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;

public class AddNewNumber extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_number);


        Switch statusAdditionNumber = (Switch) findViewById(R.id.status_additional_number);
        statusAdditionNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LinearLayout additionalNumberLayout = (LinearLayout) findViewById(R.id.additional_number_layout);
                    additionalNumberLayout.setVisibility(View.VISIBLE);
                } else {
                    LinearLayout additionalNumberLayout = (LinearLayout) findViewById(R.id.additional_number_layout);
                    additionalNumberLayout.setVisibility(View.GONE);
                }
            }
        });

        initBackButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "History").setTitle("Save")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();


        return super.onOptionsItemSelected(item);
    }
}
