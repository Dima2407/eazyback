package com.kaa_solutions.eazyback.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.kaa_solutions.eazyback.models.Contact;

import java.util.LinkedHashSet;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDB";
    public static final String TABLE_CONTACTS = "delay_contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getContactName(Context context, String phoneNumber) {
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_PHONE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table is exists " + TABLE_CONTACTS);
        onCreate(db);

    }

    public Set<Contact> readAllContacts() {
        Set<Contact> contacts = new LinkedHashSet<Contact>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(KEY_PHONE);
            {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(idIndex));
                contact.setName(cursor.getString(nameIndex));
                contact.setPhone(cursor.getString(phoneIndex));
                if (contact != null) {
                    contacts.add(contact);
                }
            }
            while (cursor.moveToNext()) ;
        }
        cursor.close();
        this.close();
        return contacts;
    }

    public void createContact(Contact contact) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_ID, contact.getId());
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PHONE, contact.getPhone());

        try {
            database.insert(TABLE_CONTACTS, null, contentValues);
        } catch (Exception e) {
            Log.e("TEST", "The number is exists in DB");
            e.printStackTrace();
        }

        this.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_CONTACTS, KEY_ID + "=" + contact.getId(), null);
    }

    public Contact readById(int id) {
        Contact contact = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTACTS + " where " + KEY_ID + "='" + id + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(KEY_PHONE);
            {
                contact = new Contact();
                contact.setId(cursor.getInt(idIndex));
                contact.setName(cursor.getString(nameIndex));
                contact.setPhone(cursor.getString(phoneIndex));
            }

        }
        cursor.close();
        return contact;
    }

    public Contact readByPhone(String phone) {
        Contact contact = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTACTS + " where " + KEY_PHONE + "='" + phone + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(KEY_PHONE);
            {
                contact = new Contact();
                contact.setId(cursor.getInt(idIndex));
                contact.setName(cursor.getString(nameIndex));
                contact.setPhone(cursor.getString(phoneIndex));
            }

        }
        cursor.close();
        return contact;
    }

    public void deleteAll() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_CONTACTS, null, null);
    }

    public Contact readByName(String name) {
        Contact contact = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTACTS + " where " + KEY_NAME + "='" + name + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(KEY_PHONE);
            {
                contact = new Contact();
                contact.setId(cursor.getInt(idIndex));
                contact.setName(cursor.getString(nameIndex));
                contact.setPhone(cursor.getString(phoneIndex));
            }

        }
        cursor.close();
        return contact;
    }
}
