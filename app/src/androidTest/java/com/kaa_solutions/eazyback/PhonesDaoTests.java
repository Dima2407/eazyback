package com.kaa_solutions.eazyback;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kaa_solutions.eazyback.db.PhonesDAO;
import com.kaa_solutions.eazyback.models.Contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)

public class PhonesDaoTests {
    private final String TAG = getClass().getSimpleName();
    Context context;
    PhonesDAO phonesDAO;


    @Before
    public void launch() {
        context = InstrumentationRegistry.getTargetContext();
        phonesDAO = new PhonesDAO(context);
    }

    @Test
    public void testReadAllContacts() {
        final ArrayList<Contact> contacts = phonesDAO.readAllContacts();
        Log.e(TAG, "testReadAllContacts: " + contacts);
    }

    @Test
    public void testCreateContact() {
        Contact contact = new Contact();
        contact.setName("Test1");
        contact.setPhone("+380631441234");
        contact.setAdditionalNumber("+380961441234");

        final int result = (int) phonesDAO.createContact(contact);
        Assert.assertEquals(result, 1);
        Log.e(TAG, "testCreateContact: " + result);
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact();
        contact.setPhone("+380631441234");
        final int result = phonesDAO.deleteContact(contact);
        Assert.assertEquals(result, 1);
    }


}
