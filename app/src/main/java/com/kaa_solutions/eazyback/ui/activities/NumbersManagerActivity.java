package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.NumbersAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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
                Toast.makeText(NumbersManagerActivity.this, "add_new_number", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.add_number_from_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NumbersManagerActivity.this, "add_number_from_book", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateListView() {
        listView = (ListView) findViewById(R.id.numbers);
        arrayOfUsers = new ArrayList<>();

        String testPhone = "+380631441234";
        Set<String> strings = new LinkedHashSet<>();
        strings.add(testPhone);
        getSharedHelper().setTargetPhoneSet(strings);

        final Set<String> targetNumbers = getSharedHelper().getTargetNumbers();
        for (String number : targetNumbers) {
            Contact contact = new Contact();
            contact.setPhone(number);
            final String contactNameFromBook = getContactDAO().getContactNameFromBook(number);
            if (contactNameFromBook != null) {
                contact.setName(contactNameFromBook);
            } else {
                contact.setName("Unknown");
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


