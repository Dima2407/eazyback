package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.ui.CallPanel;
import com.easyback.andriy.eazyback.utils.ComponentLauncher;
import com.easyback.andriy.eazyback.utils.Reflector;
import com.easyback.andriy.eazyback.utils.ViewUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;

import java.util.Set;

public final class Core {

    private final Context mContext;
    private final SharedHelper mSharedHelper;
    private CallPanel mCallPanel;
    private String mPhoneHolder;

    public Core(Context pContext, SharedHelper pSharedHelper) {
        mContext = pContext;
        mSharedHelper = pSharedHelper;
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

            long delay = mSharedHelper.getButtonsDelayInMiliSec();
            if (delay > 0) {
                launchButtonTask(delay);
                return;
            }

            if (mCallPanel == null) {
                mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
                EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Show call panel");
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

        long rejectDelay = mSharedHelper.getRejectDelayInMiliSec();

        if (rejectDelay > 0) {
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
        EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Make callback");
        long callbackDelay = mSharedHelper.getCallbackDelayInMiliSec();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ComponentLauncher.launchCallIntent(mContext, pNumber);
            }
        }, callbackDelay);
    }

    private boolean searchTargetPhone(String pIncomePhone) {
        Set<String> targetPhones = mSharedHelper.getTargetNumbers();
        boolean result = false;

        for (String phone : targetPhones) {
            if (pIncomePhone.equals(phone)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private void launchButtonTask(long pDelaySec) {
        EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Launch delay show call panel");

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
                case R.id.accept_button:
                    Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
                    mContext.sendOrderedBroadcast(i, null);
                    EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Accept button pressed in a Call panel");
                    break;

                case R.id.reject_button:
                    Reflector.disconnectCall();
                    EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Reject button pressed in a Call panel");
                    break;

                case R.id.callback_button:
                    Reflector.disconnectCall();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ComponentLauncher.launchCallIntent(mContext, mPhoneHolder);
                        }
                    }, 1000);
                    EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Callback button pressed in a Call panel");
                    break;

                case R.id.delay_callback_button:
                    Reflector.disconnectCall();
                    mSharedHelper.addDelayCallbackNumber(mPhoneHolder, mContext);
                    EasyTracker.getInstance(mContext).set(Fields.EVENT_ACTION, "Delay callback button pressed in a Call panel");
                    break;
            }

            ViewUtils.hideInterceptWindow(mContext, mCallPanel);
            mCallPanel = null;
        }
    }
}
