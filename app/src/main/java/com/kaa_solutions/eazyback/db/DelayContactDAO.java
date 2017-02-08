package com.kaa_solutions.eazyback.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;

import com.kaa_solutions.eazyback.models.Contact;

import java.util.ArrayList;

import static com.kaa_solutions.eazyback.db.DBHelper.TABLE_DELAY_CONTACTS;

public class DelayContactDAO {
    Context context;
    private DBHelper dbHelper;

    public DelayContactDAO(Context context) {
        this.dbHelper = getDbHelper(context);
        this.context = context;
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

        Cursor cursor = database.query(TABLE_DELAY_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            contacts = new ArrayList<Contact>();
            int idIndexColumn = cursor.getColumnIndex(DBHelper.COLUMN_ID);
            int nameIndexColumn = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            int phoneIndexColumn = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);
            do {
                int id = cursor.getInt(idIndexColumn);
                String name = cursor.getString(nameIndexColumn);
                String phone = cursor.getString(phoneIndexColumn);

                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setPhone(phone);
                contacts.add(contact);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return contacts;
    }

    public void addDelayCallbackNumber(String pDelayCallbackNumber) {
        String s = getContactName(pDelayCallbackNumber);

        Contact contact = new Contact();
        contact.setName(s);
        contact.setPhone(pDelayCallbackNumber);


        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_NAME, contact.getName());
        contentValues.put(DBHelper.COLUMN_PHONE, contact.getPhone());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.insert(TABLE_DELAY_CONTACTS, null, contentValues);
    }


    public void deleteDelayContact(Contact contact) {
        Contact contact1 = null;
        contact1 = findByNameOrPhone(contact);


        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_DELAY_CONTACTS, "_id = " + contact1.getId(), null);
        database.close();
    }

    public Contact findByNameOrPhone(Contact contact) {
        Contact contact1 = null;

        String clausePhone = DBHelper.COLUMN_PHONE + " = ?";
        String[] argPhone = {contact.getPhone()};

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_DELAY_CONTACTS, null, clausePhone, argPhone,
                null, null, null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String phone = cursor.getString(phoneIndex);
            contact1 = new Contact();
            contact1.setId(id);
            contact1.setName(name);
            contact1.setPhone(phone);
        }
        if (contact1 == null) {
            String clauseName = DBHelper.COLUMN_NAME + " = ?";
            String[] argName = {contact.getName()};


            cursor = database.query(TABLE_DELAY_CONTACTS, null, clauseName, argName,
                    null, null, null);
            if (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                int phoneIndex = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String phone = cursor.getString(phoneIndex);
                contact1 = new Contact();
                contact1.setId(id);
                contact1.setName(name);
                contact1.setPhone(phone);
            }


        }
        cursor.close();
        database.close();

        return contact1;
    }

    public void deleteDelayContactAll() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_DELAY_CONTACTS, null, null);
        database.close();
    }

    public Contact readByPhone(String phone) {
        //TODO: to fill
        Contact contact = null;
        return contact;
    }

    public Contact readByName(String item) {
        //TODO: to fill
        Contact contact = null;
        return contact;
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
