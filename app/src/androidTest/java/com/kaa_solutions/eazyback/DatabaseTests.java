package com.kaa_solutions.eazyback;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kaa_solutions.eazyback.db.DelayContactDAO;
import com.kaa_solutions.eazyback.models.Contact;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    @Test
    public void createDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        DelayContactDAO delayContactDAO = new DelayContactDAO(context);

        delayContactDAO.deleteDelayContactAll();


        ArrayList<Contact> arrayList = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNull(arrayList);

        if (arrayList != null) {
            for (Contact contact : arrayList) {
                Log.e(getClass().getSimpleName(), contact.toString());
            }
        }


        Contact contact = new Contact();
        contact.setPhone("+380631441234");
        delayContactDAO.addDelayCallbackNumber(contact.getPhone());

        ArrayList<Contact> arrayList1 = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNotNull(arrayList1);
        for (Contact contact1 : arrayList1) {
            Log.e(getClass().getSimpleName(), String.valueOf(contact1.getId()));
            Log.e(getClass().getSimpleName(), contact1.toString());
            Log.e(getClass().getSimpleName(), "Size:" + arrayList1.size());
        }

        Contact contact1 = delayContactDAO.findByNameOrPhone(contact);
        Assert.assertNotNull(contact1);
        Log.e(getClass().getSimpleName(), "findByNameOrPhone(contact) = " + contact1.toString() + ", id = " + contact1.getId());


        delayContactDAO.deleteDelayContact(contact);

        ArrayList<Contact> arrayList2 = delayContactDAO.getDelayCallbackNumbers();
        Assert.assertNull(arrayList2);

    }
}
