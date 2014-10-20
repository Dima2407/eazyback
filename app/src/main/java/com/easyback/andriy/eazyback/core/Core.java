package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.easyback.andriy.eazyback.Reflector;

public final class Core {

    private final Context mContext;
    private final SharedHelper mSharedHelper;

    public Core(Context pContext, SharedHelper pSharedHelper) {
        mContext = pContext;
        mSharedHelper = pSharedHelper;
    }

    public void makeParse(String pIncomePhone) {

        if (!TextUtils.isEmpty(pIncomePhone)) {
            return;
        }

        String targetPhone = mSharedHelper.getTargetPhone();
        if (!pIncomePhone.equals(targetPhone)) {
            return;
        }

        long rejectDelay = mSharedHelper.getRejectDelay();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Reflector.disconnectCall();
                makeCallback();
            }
        }, rejectDelay);
    }

    private void makeCallback() {
        long callbackDelay = mSharedHelper.getCallbackDelay();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mSharedHelper.getTargetPhone()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        }, callbackDelay);
    }
}
