package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.db.DBHelper;
import com.easyback.andriy.eazyback.models.Contact;
import com.easyback.andriy.eazyback.ui.adapters.DelayBackAdapter;
import com.easyback.andriy.eazyback.utils.ComponentLauncher;

public final class DelayCallbackNumbersActivity extends GenericActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_delay_back);
        setContentView(R.layout.activity_delay_back);

        mListView = (ListView) findViewById(R.id.list_delay_callback);
        mListView.setAdapter(new DelayBackAdapter(this, getSharedHelper().getDelayCallbackNumbers(this)));
        mListView.setEmptyView(findViewById(R.id.empty_delay_callback));
        mListView.setOnItemClickListener(new ItemClicker());

        initBackButton();

        findViewById(R.id.clearList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSharedHelper().getDelayCallbackNumbers(getBaseContext()).clear();
                new DBHelper(getBaseContext()).deleteAll();
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
            DBHelper dbHelper = new DBHelper(getBaseContext());
            Contact contact = null;
            if (dbHelper.readByPhone(item) != null) {
                contact = dbHelper.readByPhone(item);
            } else {
                contact = dbHelper.readByName(item);
            }
            dbHelper.deleteContact(contact);
            mListView.setAdapter(new DelayBackAdapter(DelayCallbackNumbersActivity.this, getSharedHelper().getDelayCallbackNumbers(getBaseContext())));
            ComponentLauncher.launchCallIntent(getApplicationContext(), contact.getPhone());

        }
    }

}
