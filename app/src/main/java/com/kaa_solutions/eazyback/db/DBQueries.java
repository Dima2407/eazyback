package com.kaa_solutions.eazyback.db;

import com.kaa_solutions.eazyback.db.constants.DatabaseColumns;

public class DBQueries {

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME = " DATETIME";
    private static final String COMA_SEP = " , ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String CREATE_DELAY_CONTACTS =
            CREATE_TABLE + DatabaseColumns.DelayContacts.TABLE_NAME + " (" +
                    DatabaseColumns.DelayContacts._ID + INTEGER_TYPE + PRIMARY_KEY + COMA_SEP +
                    DatabaseColumns.DelayContacts.COLUMN_NAME + TEXT_TYPE + COMA_SEP +
                    DatabaseColumns.DelayContacts.COLUMN_PHONE + TEXT_TYPE + COMA_SEP +
                    DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL + DATETIME +
                    ")";
    public static final String CREATE_PHONES =
            CREATE_TABLE + DatabaseColumns.Phones.TABLE_NAME + " (" +
                    DatabaseColumns.Phones._ID + INTEGER_TYPE + PRIMARY_KEY + COMA_SEP +
                    DatabaseColumns.Phones.COLUMN_NAME + TEXT_TYPE + COMA_SEP +
                    DatabaseColumns.Phones.COLUMN_PHONE + TEXT_TYPE + COMA_SEP +
                    DatabaseColumns.Phones.ADDITIONAL_NUMBER + TEXT_TYPE +
                    ")";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS ";
    public static final String DROP_DELAY_CONTACTS =
            DELETE_TABLE + DatabaseColumns.DelayContacts.TABLE_NAME;
    public static final String DROP_PHONES =
            DELETE_TABLE + DatabaseColumns.Phones.TABLE_NAME;


    private DBQueries() {
    }
}
