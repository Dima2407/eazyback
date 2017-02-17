package com.kaa_solutions.eazyback.db.constants;

import android.provider.BaseColumns;

public class DatabaseColumns {

    private DatabaseColumns() {

    }

    public static class DelayContacts implements BaseColumns {
        public static final String TABLE_NAME = "delay_contacts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_TIME_LAST_DELAYED_CALL = "time_last_delayed_call";
    }

}
