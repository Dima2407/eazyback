package com.kaa_solutions.eazyback.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_DELAY_CONTACTS = "delay_contacts";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PHONE = "phone";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "eazyback_db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(getClass().getSimpleName(), "_onCreate();");
        db.execSQL("create table " + TABLE_DELAY_CONTACTS + "(" + COLUMN_ID + " integer primary key," + COLUMN_NAME + " text," + COLUMN_PHONE + " text unique" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(getClass().getSimpleName(), "_onUpdate();");
        db.execSQL("drop table if exists " + TABLE_DELAY_CONTACTS);
        onCreate(db);

    }

    /*


    public Set<Contact> readAllContacts() {
        Set<Contact> contacts = new LinkedHashSet<Contact>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_DELAY_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
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

        contentValues.put(COLUMN_ID, contact.getId());
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_PHONE, contact.getPhone());

        try {
            database.insert(TABLE_DELAY_CONTACTS, null, contentValues);
        } catch (Exception e) {
            Log.e("TEST", "The number is exists in DB");
            e.printStackTrace();
        }

        this.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_DELAY_CONTACTS, COLUMN_ID + "=" + contact.getId(), null);
    }

    public Contact readById(int id) {
        Contact contact = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + TABLE_DELAY_CONTACTS + " where " + COLUMN_ID + "='" + id + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
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

        Cursor cursor = database.rawQuery("select * from " + TABLE_DELAY_CONTACTS + " where " + COLUMN_PHONE + "='" + phone + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
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
        database.delete(TABLE_DELAY_CONTACTS, null, null);
    }

    public Contact readByName(String name) {
        Contact contact = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + TABLE_DELAY_CONTACTS + " where " + COLUMN_NAME + "='" + name + "'", null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            {
                contact = new Contact();
                contact.setId(cursor.getInt(idIndex));
                contact.setName(cursor.getString(nameIndex));
                contact.setPhone(cursor.getString(phoneIndex));
            }

        }
        cursor.close();
        return contact;
    }*/
}
