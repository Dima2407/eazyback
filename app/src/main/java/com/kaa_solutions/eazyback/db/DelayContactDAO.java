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

            for (Contact contact : contacts) {
                Log.d(getClass().getSimpleName(), contact.toString());
            }
        }

        return contacts;
    }

    public long addDelayCallbackNumber(String pDelayCallbackNumber) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String contactName = getContactName(pDelayCallbackNumber);

        Contact contact = new Contact();
        contact.setName(contactName);
        contact.setPhone(pDelayCallbackNumber);
        contact.setTimeLastDelayedCall(getDateTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseColumns.DelayContacts.COLUMN_NAME, contact.getName());
        contentValues.put(DatabaseColumns.DelayContacts.COLUMN_PHONE, contact.getPhone());
        contentValues.put(DatabaseColumns.DelayContacts.COLUMN_TIME_LAST_DELAYED_CALL, contact.getTimeLastDelayedCall());

        return database.insert(TABLE_NAME, null, contentValues);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean deleteDelayContact(Contact contact) {
        contact = findContactByPhoneOrName(contact);
        return dbWriter.delete(TABLE_NAME, DatabaseColumns.DelayContacts._ID + "=" + contact.getId(), null) > 0;
    }

    public Contact findContactByPhoneOrName(Contact contact) {
        Contact foundContact = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String clause = DatabaseColumns.DelayContacts.COLUMN_PHONE + " = ? OR " + DatabaseColumns.DelayContacts.COLUMN_NAME + " = ?";

        String[] selectionArgs = null;

        if (contact.getName() != null) {
            selectionArgs = new String[]{contact.getName()};
        } else if (contact.getPhone() != null) {
            selectionArgs = new String[]{contact.getPhone()};
        }

        Cursor cursor = database.query(TABLE_NAME, null, clause, selectionArgs,
                null, null, null);
        if (cursor.moveToNext()) {
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

    public void deleteDelayContactAll() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }

    private String getContactName(String phoneNumber) {
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

}
