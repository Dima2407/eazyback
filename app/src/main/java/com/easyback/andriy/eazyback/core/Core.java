package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.easyback.andriy.eazyback.utils.Reflector;

import java.util.Set;

public final class Core {

    private final Context mContext;
    private final SharedHelper mSharedHelper;
    private String mTargetPhone;

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

        mTargetPhone = searchTargetPhone(pIncomePhone);

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

    private String searchTargetPhone(String pIncomePhone) {
        Set<String> targetPhones = mSharedHelper.getTargetNumbers();
        String targetPhone = null;

        for (String phone : targetPhones) {
            if (pIncomePhone.equals(phone)) {
                targetPhone = phone;
                break;
            }
        }

        return targetPhone;
    }
}
