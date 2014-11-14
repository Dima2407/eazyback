package com.easyback.andriy.eazyback.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.ui.adapters.DelayBackAdapter;
import com.easyback.andriy.eazyback.utils.ComponentLaunchControl;

public final class DelayCallbackNumbersActivity extends GenericActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_back);

        mListView = (ListView) findViewById(R.id.list_delay_callback);
        mListView.setAdapter(new DelayBackAdapter(this, getSharedHelper().getDelayCallbackNumbers()));
        mListView.setEmptyView(findViewById(R.id.empty_delay_callback));
        mListView.setOnItemClickListener(new ItemClicker());

        initBackButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatTag(getClass().getSimpleName());
    }

    private final class ItemClicker implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String number = (String) parent.getItemAtPosition(position);
            getSharedHelper().removeDelayCallbackNumber(number);
            mListView.setAdapter(new DelayBackAdapter(DelayCallbackNumbersActivity.this, getSharedHelper().getDelayCallbackNumbers()));
            ComponentLaunchControl.launchCallIntent(getApplicationContext(), number);

        }
    }

}
