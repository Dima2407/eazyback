package com.kaa_solutions.eazyback.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kaa_solutions.eazyback.db.constants.DatabaseColumns;
import com.kaa_solutions.eazyback.models.Contact;

import java.util.ArrayList;

import static com.kaa_solutions.eazyback.db.constants.DatabaseColumns.Phones.TABLE_NAME;

public class PhonesDAO {

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase dbWriter;


    public PhonesDAO(Context context) {
        this.dbHelper = getDbHelper(context);
        this.context = context;
        this.dbWriter = dbHelper.getWritableDatabase();
    }

    private DBHelper getDbHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public long createContact(Contact contact) {

        SQLiteDatabase database = dbWriter;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseColumns.Phones.COLUMN_NAME, contact.getName());
        contentValues.put(DatabaseColumns.Phones.COLUMN_PHONE, contact.getPhone());
        contentValues.put(DatabaseColumns.Phones.ADDITIONAL_NUMBER, contact.getAdditionalNumber());
        final long insert = database.insert(TABLE_NAME, null, contentValues);

        return insert;
    }

    public ArrayList<Contact> readAllContacts() {
        ArrayList<Contact> contacts = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, (DatabaseColumns.Phones.COLUMN_NAME + " ASC"));

        if (cursor.moveToNext()) {
            contacts = new ArrayList<>();
            int idIndexColumn = cursor.getColumnIndex(DatabaseColumns.Phones._ID);
            int nameIndexColumn = cursor.getColumnIndex(DatabaseColumns.Phones.COLUMN_NAME);
            int phoneIndexColumn = cursor.getColumnIndex(DatabaseColumns.Phones.COLUMN_PHONE);
            int additionalNumberIndex = cursor.getColumnIndex(DatabaseColumns.Phones.ADDITIONAL_NUMBER);
            do {
                int id = cursor.getInt(idIndexColumn);
                String name = cursor.getString(nameIndexColumn);
                String phone = cursor.getString(phoneIndexColumn);
                String additionalNumber = cursor.getString(additionalNumberIndex);

                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setPhone(phone);
                contact.setAdditionalNumber(additionalNumber);
                contacts.add(contact);
            } while (cursor.moveToNext());
            cursor.close();

            Log.d(TAG, "Table Phones: " + contacts.toString());
            Log.d(TAG, "size:" + contacts.size());
        }

        return contacts;
    }

    public int deleteContact(Contact contact) {
        return dbWriter.delete(TABLE_NAME, DatabaseColumns.Phones.COLUMN_PHONE + " = ?", new String[]{String.valueOf(contact.getPhone())});
    }

}
