package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.DelayBackAdapter;
import com.kaa_solutions.eazyback.utils.ComponentLauncher;

import java.util.ArrayList;

public final class DelayCallbackNumbersActivity extends GenericActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_delay_back);
        setContentView(R.layout.activity_delay_back);
        mListView = (ListView) findViewById(R.id.list_delay_callback);
        ArrayList<Contact> arrayOfUsers = getContactDAO().getDelayCallbackNumbers();
        DelayBackAdapter adapter;
        if (arrayOfUsers != null) {
            adapter = new DelayBackAdapter(this, arrayOfUsers);
            mListView.setAdapter(adapter);
        } else {
            mListView.setAdapter(null);
        }

        mListView.setEmptyView(findViewById(R.id.empty_delay_callback));
        mListView.setOnItemClickListener(new ItemClicker());

        initBackButton();

        findViewById(R.id.clearList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContactDAO().deleteDelayContactAll();
                mListView.setAdapter(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
    }

    private final class ItemClicker implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = String.valueOf(parent.getItemAtPosition(position));
            Contact contact;
            if (getContactDAO().readByPhone(item) != null) {
                contact = getContactDAO().readByPhone(item);
            } else {
                contact = getContactDAO().readByName(item);
            }
            getContactDAO().deleteDelayContact(contact);
            ArrayList<Contact> arrayOfUsers = getContactDAO().getDelayCallbackNumbers();
            DelayBackAdapter adapter = new DelayBackAdapter(DelayCallbackNumbersActivity.this, arrayOfUsers);
            mListView.setAdapter(adapter);
            ComponentLauncher.launchCallIntent(getApplicationContext(), contact.getPhone());

        }
    }

}
