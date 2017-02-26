package com.kaa_solutions.eazyback;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kaa_solutions.eazyback.core.SharedHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SharedPreferencesTest {
    private final String TAG = getClass().getSimpleName();
    Context context;
    SharedHelper mSharedHelper;

    @Before
    public void launch() {
        context = InstrumentationRegistry.getTargetContext();
        mSharedHelper = new SharedHelper(context);
    }

    @Test
    public void testGetAcceptMargin() {

        final int firstLeft = mSharedHelper.getAcceptButtonMarginLeft();
        Log.e(TAG, "!acceptButtonMarginLeft: " + firstLeft);
        final int firstTop = mSharedHelper.getAcceptButtonMarginTop();
        Log.e(TAG, "!acceptButtonMarginTop: " + firstTop);
        mSharedHelper.setAcceptButtonMarginLeft(500);
        final int acceptButtonMarginLeft1 = mSharedHelper.getAcceptButtonMarginLeft();
        Assert.assertTrue(acceptButtonMarginLeft1 == 500);
        Log.e(TAG, "acceptButtonMarginLeft1: " + acceptButtonMarginLeft1);

        mSharedHelper.setAcceptButtonMarginTop(600);
        final int acceptButtonMarginTop1 = mSharedHelper.getAcceptButtonMarginTop();
        Assert.assertTrue(acceptButtonMarginTop1 == 600);
        Log.e(TAG, "acceptButtonMarginTop1: " + acceptButtonMarginTop1);
    }

    @Test
    public void testGetDelayMargin() {
        final int delayButtonMarginLeft = mSharedHelper.getDelayButtonMarginLeft();
        Log.e(TAG, "delayButtonMarginLeft: " + delayButtonMarginLeft);

        mSharedHelper.setDelayButtonMarginLeft(150);

        final int delayButtonMarginLeft1 = mSharedHelper.getDelayButtonMarginLeft();
        Log.e(TAG, "delayButtonMarginLeft1: " + delayButtonMarginLeft1);

        final int acceptButtonMarginLeft = mSharedHelper.getAcceptButtonMarginLeft();
        Log.e(TAG, "acceptButtonMarginLeft: " + acceptButtonMarginLeft);

        mSharedHelper.setAcceptButtonMarginLeft(150);
        final int acceptButtonMarginLeft1 = mSharedHelper.getAcceptButtonMarginLeft();
        Log.e(TAG, "acceptButtonMarginLeft1: " + acceptButtonMarginLeft1);

        final int testSP = mSharedHelper.getTestSP();
        Log.e(TAG, "getTestSP: " + testSP);

        mSharedHelper.setTestSP(150);
        final int testSP1 = mSharedHelper.getTestSP();
        Assert.assertTrue(testSP1 == 150);
        Log.e(TAG, "getTestSP1: " + testSP1);
    }

}
