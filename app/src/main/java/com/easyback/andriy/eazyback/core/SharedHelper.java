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

    public void setTargetSet(String pTargetTelephone) {
        mSharedPreferences.edit().putString(TARGET_PHONE, pTargetTelephone).commit();
    }

    public String getTargetPhone() {
        return mSharedPreferences.getString(TARGET_PHONE, "");
    }

    public int getRejectDelay() {
        return mSharedPreferences.getInt(REJECT_DELAY, 0);
    }

    public int getCallbackDelay() {
        return mSharedPreferences.getInt(CALL_BACK_DELAY, 0);
    }

    public void setRejectDelay(int pRejectDelay) {
        mSharedPreferences.edit().putInt(REJECT_DELAY, pRejectDelay).commit();
    }

    public void setCallbackDelay(int pCallbackDelay) {
        mSharedPreferences.edit().putInt(CALL_BACK_DELAY, pCallbackDelay).commit();
    }
}
