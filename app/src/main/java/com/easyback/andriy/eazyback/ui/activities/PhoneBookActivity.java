package com.easyback.andriy.eazyback.ui.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.easyback.andriy.eazyback.R;

public class PhoneBookActivity extends Activity {

    ListView myList;
    private String listViewArray[] = {"ONE", "TWO", "THREE", "FOUR", "FIVE",
            "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN"};

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
        myList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewArray));
        getListViewSize(myList);


    }

    void getAddressBook() {
        final Cursor query = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (query.moveToFirst()) {
            final int contact = query.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                final String string = query.getString(contact);

            } while (query.moveToNext());
        }
    }

}
