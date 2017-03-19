package com.kaa_solutions.eazyback.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.kaa_solutions.eazyback.db.constants.DatabaseColumns;
import com.kaa_solutions.eazyback.models.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.kaa_solutions.eazyback.db.constants.DatabaseColumns.DelayContacts.TABLE_NAME;


public class DelayContactDAO {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase dbWriter;

    public DelayContactDAO(Context context) {
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


    public ArrayList<Contact> getDelayCallbackNumbers() {
        ArrayList<Contact> contacts = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, (DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL + " DESC"));
        if (cursor.moveToNext()) {
            contacts = new ArrayList<Contact>();
            int idIndexColumn = cursor.getColumnIndex(DatabaseColumns.DelayContacts._ID);
            int nameIndexColumn = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_NAME);
            int phoneIndexColumn = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_PHONE);
            int timeLastDelayedCallIndex = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL);
            do {
                int id = cursor.getInt(idIndexColumn);
                String name = cursor.getString(nameIndexColumn);
                String phone = cursor.getString(phoneIndexColumn);
                String time = cursor.getString(timeLastDelayedCallIndex);

                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setPhone(phone);
                contact.setTimeLastDelayedCall(time);
                contacts.add(contact);
            } while (cursor.moveToNext());
            cursor.close();

            Log.d(TAG, "Delay contacts: " + contacts.toString());
            Log.d(TAG, "size:" + contacts.size());
        }

        return contacts;
    }

    public void addDelayCallbackNumber(String pDelayCallbackNumber) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String contactName = getContactNameFromBook(pDelayCallbackNumber);

        Contact contact = new Contact();
        contact.setPhone(pDelayCallbackNumber);
        contact.setName(contactName);

        ContentValues contentValues = new ContentValues();
        if (!isExists(contact)) {
            contact.setTimeLastDelayedCall(getDateTime());

            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_NAME, contact.getName());
            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_PHONE, contact.getPhone());
            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL, contact.getTimeLastDelayedCall());
            database.insert(TABLE_NAME, null, contentValues);
        } else {
            contact = findContactByPhoneOrNameInDelayedList(contact);
            contact.setTimeLastDelayedCall(getDateTime());
            contentValues.put(DatabaseColumns.DelayContacts._ID, contact.getId());
            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_NAME, contact.getName());
            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_PHONE, contact.getPhone());
            contentValues.put(DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL, contact.getTimeLastDelayedCall());
            String[] args = {String.valueOf(contact.getId())};
            database.update(TABLE_NAME, contentValues, DatabaseColumns.DelayContacts._ID + " = ?", args);

        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void deleteDelayContact(Contact contact) {

        try {
            dbWriter.delete(TABLE_NAME, DatabaseColumns.DelayContacts._ID + " = ?", new String[]{String.valueOf(contact.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Contact findContactByPhoneOrNameInDelayedList(Contact contact) {
        Contact foundContact = null;

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "select * from " + TABLE_NAME + " where name=? OR phone = ?";


        String args = null;

        if (contact.getName() != null) {
            args = contact.getName();
        } else if (contact.getPhone() != null) {
            args = contact.getPhone();
        }

        Cursor cursor = database.rawQuery(query, new String[]{args});
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseColumns.DelayContacts._ID);
            int nameIndex = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_PHONE);
            int timeIndex = cursor.getColumnIndex(DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String phone = cursor.getString(phoneIndex);
            String time = cursor.getString(timeIndex);
            foundContact = new Contact();
            foundContact.setId(id);
            foundContact.setName(name);
            foundContact.setPhone(phone);
            foundContact.setTimeLastDelayedCall(time);
        }
        cursor.close();
        return foundContact;
    }

    public boolean isExists(Contact contact) {
        boolean isExists = false;
        final Contact contactByPhoneOrName = findContactByPhoneOrNameInDelayedList(contact);
        if (contactByPhoneOrName != null) {
            isExists = true;
        }

        return isExists;
    }

    public void deleteDelayContactAll() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }

    public String getContactNameFromBook(String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    public void dropAndCreateTable() {
        dbWriter.execSQL(DBQueries.DROP_DELAY_CONTACTS);
        dbWriter.execSQL(DBQueries.CREATE_DELAY_CONTACTS);
    }


}
