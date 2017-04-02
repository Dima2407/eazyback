package com.kaa_solutions.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewNumber extends GenericActivity {

    private EditText name, phone, additionalNumber;
    private Contact contact;
    private Pattern patternName = Pattern.compile("\\w[\\w+ ]+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_number);
        setTitle(R.string.title_add_new_number);
        initEditText();

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

    private void initEditText() {
        name = (EditText) findViewById(R.id.add_new_contact_name_editText);
        phone = (EditText) findViewById(R.id.add_new_contact_phone_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "History").setTitle(R.string.save)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                createNewContact();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewContact() {

        if (!patternName.matcher(name.getText()).matches()) {
            name.setError(getString(R.string.error_incorrect_name));
            return;
        }

        if (!Patterns.PHONE.matcher(phone.getText()).matches()) {
            phone.setError(getString(R.string.error_incorrect_phone_number));
            return;
        }

        Contact contact = new Contact();
        contact.setName(String.valueOf(name.getText()));
        contact.setPhone(String.valueOf(phone.getText()));
        getPhonesDAO().createContact(contact);
        startActivity(new Intent(this, NumbersManagerActivity.class));
        Log.e(getClass().getSimpleName(), "onOptionsItemSelected: " + getPhonesDAO().readAllContacts().toString());
    }
}
