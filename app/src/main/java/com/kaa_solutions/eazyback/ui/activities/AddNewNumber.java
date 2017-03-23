package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
