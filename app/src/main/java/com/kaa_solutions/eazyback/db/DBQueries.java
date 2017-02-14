package com.kaa_solutions.eazyback.db;

import com.kaa_solutions.eazyback.db.constants.DatabaseColumns;

public class DbQueries {
    private static final String BOOLEAN_DEFAULT = " DEFAULT 0";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = INTEGER_TYPE + BOOLEAN_DEFAULT;
    private static final String COMA_SEP = " , ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String CREATE_DELAY_CONTACTS =
            CREATE_TABLE + DatabaseColumns.DelayContacts.TABLE_NAME + " (" +
                    DatabaseColumns.DelayContacts._ID + INTEGER_TYPE + PRIMARY_KEY + COMA_SEP +
                    DatabaseColumns.DelayContacts.COLUMN_NAME + TEXT_TYPE + COMA_SEP +
                    DatabaseColumns.DelayContacts.COLUMN_PHONE + TEXT_TYPE + COMA_SEP +
                    ")";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS ";
    public static final String DROP_DELAY_CONTACTS =
            DELETE_TABLE + DatabaseColumns.DelayContacts.TABLE_NAME;

    private DbQueries() {
    }
}
