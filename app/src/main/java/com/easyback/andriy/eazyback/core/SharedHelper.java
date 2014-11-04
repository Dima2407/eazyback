package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    private static final String NAME = "EazyBack";
    private static final String ACTIVATE_FLAG = "activate_flag";
    private static final String TARGET_PHONE = "target_phone";
    private static final String REJECT_DELAY = "reject_delay";
    private static final String CALL_BACK_DELAY = "callback_delay";

    private final SharedPreferences mSharedPreferences;

    public SharedHelper(Context pContext) {
        mSharedPreferences = pContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setActivate(boolean pIsActivate) {
        mSharedPreferences.edit().putBoolean(ACTIVATE_FLAG, pIsActivate).commit();
    }

    public boolean getIsActivate() {
        return mSharedPreferences.getBoolean(ACTIVATE_FLAG, false);
    }

    public void setTargetPhone(String pTargetTelephone) {
        mSharedPreferences.edit().putString(TARGET_PHONE, pTargetTelephone).commit();
    }

    public String getTargetPhone() {
        return mSharedPreferences.getString(TARGET_PHONE, "");
    }

    public long getRejectDelay() {
        long delay = mSharedPreferences.getLong(REJECT_DELAY, -1);

        if (delay != -1) {
            return delay / 1000;
        }
        return delay;
    }

    public long getCallbackDelay() {
        long delay = mSharedPreferences.getLong(CALL_BACK_DELAY, -1);

        if (delay != -1) {
            return delay / 1000;
        }
        return delay;
    }

    public void setRejectDelay(String pRejectDelay) {
        long reject = Long.parseLong(pRejectDelay) * 1000;
        mSharedPreferences.edit().putLong(REJECT_DELAY, reject).commit();
    }

    public void setCallbackDelay(String pCallbackDelay) {
        long callback = Long.parseLong(pCallbackDelay) * 1000;
        mSharedPreferences.edit().putLong(CALL_BACK_DELAY, callback).commit();
    }
}
