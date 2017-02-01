package com.easyback.andriy.eazyback.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.easyback.andriy.eazyback.R;

import java.util.ArrayList;

public class PhoneBookActivity extends GenericActivity {

    private static final String TAG = "PhoneBookActivity";

    private static ArrayList<String> listViewArray = new ArrayList<String>();
    ListView myList;

    public static void getListViewSize(ListView myListView) {
        listViewArray.add("One");
        listViewArray.add("Two");
        listViewArray.add("Three");
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
        myList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewArray));
        getListViewSize(myList);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "test", Toast.LENGTH_SHORT);
                Intent Intent = new Intent(view.getContext(), NumbersManagerActivity.class);
                view.getContext().startActivity(Intent);
            }
        });
        initBackButton();

    }

    void getAddressBook() {
        final Cursor query = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (query.moveToFirst()) {
            listViewArray.clear();
            System.out.println(query.getColumnNames());
            final int contact = query.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                final String string = query.getString(contact);
                listViewArray.add(string);

            } while (query.moveToNext());
        }
    }

     /* public void fpGetAndroidContacts() {
        ArrayList<AddressBookActivity.AndroidContacts> arrayListAndroidContacts = new ArrayList<AddressBookActivity.AndroidContacts>();


        Cursor query = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception e) {
            Log.e(TAG, "Contact error!");
        }

        if (query.getCount()>0){
            while (query.moveToNext()){
                Contact androidContact = new AddressBookActivity.AndroidContacts();
                String contactId = query.getString(query.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName =  query.getString(query.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                androidContact.name = contactName;

                int hasPhoneNumber = Integer.parseInt(query.getString(query.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber>0){
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            , null
                            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            , new String[]{contactId}
                            ,null

                    );

                    while (phoneCursor.moveToNext()){
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        androidContact.phone=phoneNumber;
                    }
                    phoneCursor.close();
                }
                arrayListAndroidContacts.add(androidContact);

            }

        }



    class Contact {
        public String name = "";
        public String phone = "";
        public int id = 0;

    }
*/
}
