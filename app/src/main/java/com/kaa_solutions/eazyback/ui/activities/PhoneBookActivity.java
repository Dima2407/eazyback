package com.kaa_solutions.eazyback.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.PhonebookAdapter;

import java.util.ArrayList;
import java.util.Collections;

import static com.kaa_solutions.eazyback.core.SharedHelper.AMOUNT_PHONES_NUMBER;

public class PhoneBookActivity extends GenericActivity {

    private ArrayList<Contact> arrayOfUsers;
    private ListView myList;
    private PhonebookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_phone_book);
        setContentView(R.layout.activity_phone_book);

        myList = (ListView) findViewById(R.id.listView);
        initListView();

        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                if (getSharedHelper().getTargetNumbers().size() < AMOUNT_PHONES_NUMBER) {
                    Contact contact = (Contact) parent.getAdapter().getItem(position);

                    getSharedHelper().getTargetNumbers().add(contact.getPhone());

                    Toast.makeText(getApplicationContext(), R.string.added_contact,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_added_contact,
                            Toast.LENGTH_LONG).show();
                }

                Intent Intent = new Intent(itemClicked.getContext(), NumbersManagerActivity.class);
                startActivity(Intent);

            }
        });

        initBackButton();
    }

    private void initListView() {
        arrayOfUsers = getAddressBook();
        if (arrayOfUsers != null) {
            adapter = new PhonebookAdapter(this, arrayOfUsers);
            myList.setAdapter(adapter);
        } else {
            myList.setAdapter(null);
        }

    }


    private ArrayList<Contact> getAddressBook() {
        final ArrayList<Contact> contacts = new ArrayList<Contact>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getContentResolver().query(uri, projection, null, null, null);

        int indexId = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexPhone = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        if (people.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(indexId);
                contact.setName(people.getString(indexName));
                contact.setPhone(people.getString(indexPhone));

                contacts.add(contact);

            } while (people.moveToNext());
        }
        Collections.sort(contacts);
        return contacts;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NumbersManagerActivity.class));
    }
}
