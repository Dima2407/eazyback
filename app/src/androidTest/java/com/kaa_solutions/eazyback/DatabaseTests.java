package com.kaa_solutions.eazyback;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kaa_solutions.eazyback.db.DelayContactDAO;
import com.kaa_solutions.eazyback.models.Contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {
    private final String TAG = getClass().getSimpleName();
    Context context;


    @Before
    public void launch() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void addContact() {

        DelayContactDAO delayContactDAO = new DelayContactDAO(context);

        delayContactDAO.deleteDelayContactAll();
        ArrayList<Contact> delayCallbackNumbers1 = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNull(delayCallbackNumbers1);

        Contact contact = new Contact();
        contact.setPhone("+380631441234");
        delayContactDAO.createDelayCallbackNumber(contact.getPhone());

        Contact contact2 = new Contact();
        contact.setPhone("+380961441234");
        delayContactDAO.createDelayCallbackNumber(contact2.getPhone());

        Contact contact3 = new Contact();
        contact.setPhone("+380961441234");
        delayContactDAO.createDelayCallbackNumber(contact3.getPhone());

        ArrayList<Contact> delayCallbackNumbers = delayContactDAO.getDelayCallbackNumbers();
        for (Contact delayCallbackNumber : delayCallbackNumbers) {
            Log.d(TAG, delayCallbackNumber.toString());
        }

    }

    @Test
    public void testGetAllDelayContacts() throws InterruptedException {
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);

        ArrayList<Contact> contacts = delayContactDAO.getDelayCallbackNumbers();
        for (Contact contact : contacts) {
            Log.d(TAG, "testMethodGetAllDelayContacts: " + contact.toString());
        }
    }

    @Test
    public void testUpdateContact() throws InterruptedException {
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);
        ArrayList<Contact> delayCallbackNumbers;

        delayContactDAO.deleteDelayContactAll();
        delayCallbackNumbers = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNull(delayCallbackNumbers);

        Contact contact = new Contact();
        contact.setPhone("+380631441234");
        delayContactDAO.createDelayCallbackNumber(contact.getPhone());

        delayCallbackNumbers = delayContactDAO.getDelayCallbackNumbers();
        for (Contact delayCallbackNumber : delayCallbackNumbers) {
            Log.d(TAG, "delayCallbackNumber: " + delayCallbackNumber);
        }
        Log.d(TAG, "before updating...");
        Contact contactUpdate = delayContactDAO.findContactByPhoneOrNameInDelayedList(contact);
        Log.d(TAG, "contactUpdate: " + contactUpdate);
        Assert.assertNotNull(contactUpdate);
        for (Contact delayCallbackNumber : delayCallbackNumbers) {
            Log.d(TAG, "delayCallbackNumber: " + delayCallbackNumber);
        }
        Thread.sleep(2000);
        delayContactDAO.createDelayCallbackNumber(contactUpdate.getPhone());
        for (Contact delayCallbackNumber : delayCallbackNumbers) {
            Log.d(TAG, "after updating...");
            Log.d(TAG, "delayCallbackNumber: " + delayCallbackNumber);
        }
    }


    @Test
    public void testFindByPhoneOrName() {
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);

        delayContactDAO.deleteDelayContactAll();

        Contact contact = new Contact();
        contact.setPhone("+380631441234");

        delayContactDAO.createDelayCallbackNumber(contact.getPhone());

        Contact foundContact = delayContactDAO.findContactByPhoneOrNameInDelayedList(contact);
        Assert.assertNotNull(foundContact);
        Log.d(TAG, "Found contact: " + foundContact);
    }

    @Test
    public void testDeleteContact() {
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);

        delayContactDAO.deleteDelayContactAll();

        Contact contact = new Contact();
        contact.setPhone("+380631441234");

        delayContactDAO.createDelayCallbackNumber(contact.getPhone());

        Contact foundContact = delayContactDAO.findContactByPhoneOrNameInDelayedList(contact);
        Assert.assertNotNull(foundContact);
        Log.d(TAG, "Found contact: " + foundContact);

        delayContactDAO.deleteDelayContact(contact);
    }


    @Test
    public void testDeleteAllContacts() {
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);
        Contact contact = new Contact();
        contact.setPhone("+380637777777");
        delayContactDAO.createDelayCallbackNumber(contact.getPhone());
        ArrayList<Contact> delayCallbackNumbers = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertTrue(delayCallbackNumbers.size() > 0);

        delayContactDAO.deleteDelayContactAll();
        delayCallbackNumbers = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNull(delayCallbackNumbers);


    }

}
