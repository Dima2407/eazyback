package com.easyback.andriy.eazyback.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.models.Contact;

import java.util.ArrayList;

import static com.easyback.andriy.eazyback.core.SharedHelper.AMOUNT_PHONES_NUMBER;

public class PhoneBookActivity extends GenericActivity {

    private static final String TAG = "PhoneBookActivity";

    private static ArrayList<Contact> listViewArray = new ArrayList<Contact>();
    ListView myList;

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);

        myList = (ListView) findViewById(R.id.listView);


        getAddressBook();
        myList.setAdapter(new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, listViewArray));
        getListViewSize(myList);


        for (Contact contact : listViewArray) {
            Log.e(TAG, contact.toString());
        }


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
                itemClicked.getContext().startActivity(Intent);

            }
        });

        initBackButton();
    }

    void getAddressBook() {
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

                listViewArray.add(contact);

            } while (people.moveToNext());
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NumbersManagerActivity.class));
    }
}
