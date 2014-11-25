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

    public void makeParse(final String pIncomePhone) {


        if (TextUtils.isEmpty(pIncomePhone)) {
            Log.d("C", "empty-income");
            return;
        }

        if (mSharedHelper.getIsActivateManualMode()) {

            if (mSharedHelper.getManualInterceptMode()) {
                if (!searchTargetPhone(pIncomePhone)) {
                    Log.d("C", "non-find in manual");
                    return;
                }
            }

            Log.d("C", "activate manual");
            mPhoneHolder = pIncomePhone;

            long delay = mSharedHelper.getButtonsDelayInMiliSec();
            if (delay > 0) {
                launchButtonTask(delay);
                return;
            }

            mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
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
                    break;

                case R.id.reject_button:
                    Reflector.disconnectCall();
                    break;

                case R.id.callback_button:
                    Reflector.disconnectCall();
                    ComponentLauncher.launchCallIntent(mContext, mPhoneHolder);
                    mPhoneHolder = null;
                    break;

                case R.id.delay_callback_button:
                    Reflector.disconnectCall();
                    mSharedHelper.addDelayCallbackNumber(mPhoneHolder);
                    mPhoneHolder = null;
                    break;
            }

            ViewUtils.hideInterceptWindow(mContext, mCallPanel);
        }
    }
}
