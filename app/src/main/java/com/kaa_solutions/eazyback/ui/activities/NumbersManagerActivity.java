package com.kaa_solutions.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.NumbersAdapter;

import java.util.ArrayList;
import java.util.Set;

import static com.kaa_solutions.eazyback.core.SharedHelper.AMOUNT_PHONES_NUMBER;

public final class NumbersManagerActivity extends GenericActivity {

    private ListView listView;
    private ArrayList<Contact> arrayOfUsers;
    private com.getbase.floatingactionbutton.FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_numbers);
        setContentView(R.layout.activity_number);
        buildFam();
        inflateListView();
    }

    private void buildFam() {
        findViewById(R.id.add_new_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSharedHelper().getTargetNumbers().size() < AMOUNT_PHONES_NUMBER) {
//                startActivity(new Intent(getApplicationContext(), AddNewNumberActivity.class));
                } else {
                    Toast.makeText(NumbersManagerActivity.this, R.string.listOfNumbersIsFull, Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.add_number_from_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSharedHelper().getTargetNumbers().size() < AMOUNT_PHONES_NUMBER) {
                    startActivity(new Intent(getApplicationContext(), PhoneBookActivity.class));
                } else {
                    Toast.makeText(NumbersManagerActivity.this, R.string.listOfNumbersIsFull, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inflateListView() {
        listView = (ListView) findViewById(R.id.numbers);
        arrayOfUsers = new ArrayList<>();

        final Set<String> targetNumbers = getSharedHelper().getTargetNumbers();
        for (String number : targetNumbers) {
            Contact contact = new Contact();
            contact.setPhone(number);
            final String contactNameFromBook = getContactDAO().getContactNameFromBook(number);
            if (contactNameFromBook != null) {
                contact.setName(contactNameFromBook);
            } else {
                contact.setName(String.valueOf(R.string.unknown));
            }
            arrayOfUsers.add(contact);
        }

        NumbersAdapter adapter;
        if (arrayOfUsers != null) {
            adapter = new NumbersAdapter(this, arrayOfUsers);
            listView.setAdapter(adapter);
        } else {
            listView.setAdapter(null);
        }
    }
}


