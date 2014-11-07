package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.ui.CallPanel;
import com.easyback.andriy.eazyback.utils.Reflector;
import com.easyback.andriy.eazyback.utils.ViewUtils;

import java.util.Set;

public final class Core {

    private final Context mContext;
    private final SharedHelper mSharedHelper;
    private String mTargetPhone;
    private CallPanel mCallPanel;

    public Core(Context pContext, SharedHelper pSharedHelper) {
        mContext = pContext;
        mSharedHelper = pSharedHelper;
    }

    public void makeParse(String pIncomePhone) {

        if (!mSharedHelper.getIsActivate()) {
            return;
        }

        if (TextUtils.isEmpty(pIncomePhone)) {
            return;
        }

        if (mSharedHelper.getIsActivateManualMode()) {
            mTargetPhone = pIncomePhone;
            mCallPanel = ViewUtils.showInterceptWindow(mContext, new Clicker());
            return;
        }

        if(searchTargetPhone(pIncomePhone)){
            mTargetPhone = pIncomePhone;
            return;
        }

        if (TextUtils.isEmpty(mTargetPhone)) {
            return;
        }

        long rejectDelay = mSharedHelper.getRejectDelayInMiliSec();

        if (rejectDelay > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Reflector.disconnectCall();
                    makeCallback();
                }
            }, rejectDelay);
        }
    }

    private void makeCallback() {
        long callbackDelay = mSharedHelper.getCallbackDelayInMiliSec();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mTargetPhone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        }, callbackDelay);
    }

    private void makeCallbackImmidetly() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mTargetPhone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(callIntent);

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
                    makeCallbackImmidetly();
                    break;
            }

            ViewUtils.hideInterceptWindow(mContext, mCallPanel);
        }
    }
}
