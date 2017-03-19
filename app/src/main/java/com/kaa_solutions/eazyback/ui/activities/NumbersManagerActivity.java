package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.NumbersAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class NumbersManagerActivity extends GenericActivity {

    private List<EditText> mTelephoneCells;
    private Button openAddressBook;
    private ListView listView;
    private ArrayList<Contact> arrayOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_numbers);
        setContentView(R.layout.activity_number);
        setFAB();

        inflateListView();



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

    private void setFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NumbersManagerActivity.this, "It doesn't work yet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
