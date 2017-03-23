package com.kaa_solutions.eazyback.core;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.db.DelayContactDAO;
import com.kaa_solutions.eazyback.db.PhonesDAO;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.CallPanel;
import com.kaa_solutions.eazyback.utils.ComponentLauncher;
import com.kaa_solutions.eazyback.utils.Reflector;
import com.kaa_solutions.eazyback.utils.ViewUtils;

import java.util.ArrayList;

public final class Core {

    private final String TAG = getClass().getSimpleName();
    private final Context mContext;
    private final SharedHelper mSharedHelper;
    private final DelayContactDAO mContactDAO;
    private final PhonesDAO mPhonesDAO;
    private CallPanel mCallPanel;
    private String mPhoneHolder;

    public Core(Context pContext, SharedHelper pSharedHelper, DelayContactDAO contactDAO, PhonesDAO PhonesDAO) {
        mContext = pContext;
        mSharedHelper = pSharedHelper;
        mContactDAO = contactDAO;
        mPhonesDAO = PhonesDAO;
    }

    public void hideCallPanelWindow() {

        if (!mSharedHelper.getAutoHideCallPanel()) {
            return;
        }

        if (mCallPanel == null || mContext == null) {
            return;
        }

        ViewUtils.hideInterceptWindow(mContext, mCallPanel);
        mCallPanel = null;
    }

    public void makeParse(final String pIncomePhone) {

        if (TextUtils.isEmpty(pIncomePhone)) {
            Log.d("C", "empty-income");
            return;
        }

        mPhoneHolder = pIncomePhone;

        if (mSharedHelper.getIsActivateManualMode()) {

            if (mSharedHelper.getManualInterceptMode()) {
                if (!searchTargetPhone(pIncomePhone)) {
                    Log.d("C", "non-find in manual");
                    return;
                }
            }

            long delay = mSharedHelper.getButtonsDelayInMilSec();
            if (delay > 0) {
                launchButtonTask(delay);
                return;
            }


            if (mCallPanel == null) {
                mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
            }
            return;
        }


        if (!mSharedHelper.getIsCallbacksActivate()) {
            return;
        }


        if (!searchTargetPhone(pIncomePhone)) {
            Log.d("C", "non-find");
            return;
        }

        long rejectDelay = mSharedHelper.getRejectDelayInMilSec();

        if (rejectDelay >= 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Reflector.disconnectCall();
                    makeCallback(pIncomePhone);
                }
            }, rejectDelay);
        }
    }

    private void makeCallback(final String pNumber) {
        long callbackDelay = mSharedHelper.getCallbackDelayInMilSec();
        if (callbackDelay == 0) {
            callbackDelay = 1000;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ComponentLauncher.launchCallIntent(mContext, pNumber);
            }
        }, callbackDelay);
    }

    private boolean searchTargetPhone(String pIncomePhone) {
        ArrayList<Contact> contacts = mPhonesDAO.readAllContacts();
        boolean result = false;

        for (Contact contact : contacts) {
            if (pIncomePhone.equals(contact.getPhone())) {
                result = true;
                break;
            }
        }

        return result;
    }

    private void launchButtonTask(long pDelaySec) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCallPanel == null) {
                    mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
                }
            }
        }, pDelaySec);
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.accept_image:
                    Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
                    mContext.sendOrderedBroadcast(i, null);
                    Log.e(TAG, "onClick: accept_image");
                    break;

                case R.id.reject_image:
                    Reflector.disconnectCall();
                    Log.e(TAG, "onClick: reject_image");
                    break;

                case R.id.callback_image:
                    Reflector.disconnectCall();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ComponentLauncher.launchCallIntent(mContext, mPhoneHolder);
                        }
                    }, 1000);
                    Log.e(TAG, "onClick: callback_image");
                    break;

                case R.id.delay_image:
                    Reflector.disconnectCall();
                    mContactDAO.createDelayCallbackNumber(mPhoneHolder);
                    Log.e(TAG, "onClick: delay_image");
                    break;
                default:
                    Log.e(TAG, "onClick: another ID");
            }

            ViewUtils.hideInterceptWindow(mContext, mCallPanel);
            mCallPanel = null;
        }
    }
}
