package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.DelayBackAdapter;
import com.kaa_solutions.eazyback.utils.ComponentLauncher;

import java.util.ArrayList;

public final class DelayCallbackNumbersActivity extends GenericActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ListView mListView;
    private DelayBackAdapter adapter;
    private Button btnClearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_delay_back);
        setContentView(R.layout.activity_delay_back);
        mListView = (ListView) findViewById(R.id.list_delay_callback);
        btnClearList = (Button) findViewById(R.id.clearList);

        ArrayList<Contact> arrayOfUsers = getDelayedContactDAO().getDelayCallbackNumbers();

        if (arrayOfUsers != null) {
            adapter = new DelayBackAdapter(this, arrayOfUsers);
            mListView.setAdapter(adapter);
            btnClearList.setEnabled(true);
        } else {
            mListView.setAdapter(null);
            btnClearList.setEnabled(false);
        }
        mListView.setEmptyView(findViewById(R.id.empty_delay_callback));
        btnClearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelayedContactDAO().deleteDelayContactAll();
                mListView.setAdapter(null);
                btnClearList.setEnabled(false);
            }
        });
        initBackButton();
        setOnItemClickListener();
    }

    private void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                                                 Contact contact = (Contact) parent.getAdapter().getItem(position);
                                                 getDelayedContactDAO().deleteDelayContact(contact);

                                                 ArrayList<Contact> arrayOfUsers = getDelayedContactDAO().getDelayCallbackNumbers();

                                                 if (arrayOfUsers != null) {
                                                     adapter.notifyDataSetChanged();
                                                     mListView.setAdapter(adapter);
                                                 } else {
                                                     mListView.setAdapter(null);
                                                     btnClearList.setEnabled(false);
                                                 }
                                                 ComponentLauncher.launchCallIntent(getApplicationContext(), contact.getPhone());
                                             }
                                         }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Contact> arrayOfUsers = getDelayedContactDAO().getDelayCallbackNumbers();
        DelayBackAdapter adapter;
        if (arrayOfUsers != null) {
            adapter = new DelayBackAdapter(DelayCallbackNumbersActivity.this, arrayOfUsers);
            mListView.setAdapter(adapter);
        } else {
            mListView.setAdapter(null);
        }
    }
}
