package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public final class SharedHelper {

    private static final String NAME = "EazyBack";
    private static final String ACTIVATE_FLAG = "activate_flag";
    private static final String ACTIVATE_MANUAL_FLAG = "activate_manual_flag";
    private static final String TARGET_PHONE_SET = "target_phone_set";
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

    public void setActivateManualMode(boolean pIsActivateManualMode) {
        mSharedPreferences.edit().putBoolean(ACTIVATE_MANUAL_FLAG, pIsActivateManualMode).commit();
    }

    public boolean getIsActivateManualMode() {
        return mSharedPreferences.getBoolean(ACTIVATE_MANUAL_FLAG, false);
    }

    public Set<String> getTargetNumbers() {
        return mSharedPreferences.getStringSet(TARGET_PHONE_SET, new HashSet<String>());
    }

    public void setTargetPhoneSet(Set<String> pTargetPhoneSet) {
        mSharedPreferences.edit().remove(TARGET_PHONE_SET).commit();
        mSharedPreferences.edit().putStringSet(TARGET_PHONE_SET, pTargetPhoneSet).commit();
    }

    public long getRejectDelayInSec() {
        long delay = mSharedPreferences.getLong(REJECT_DELAY, -1);

        if (delay != -1) {
            return delay / 1000;
        }
        return delay;
    }

    public long getCallbackDelayInSec() {
        long delay = mSharedPreferences.getLong(CALL_BACK_DELAY, -1);

        if (delay != -1) {
            return delay / 1000;
        }
        return delay;
    }

    public long getRejectDelayInMiliSec() {
        return mSharedPreferences.getLong(REJECT_DELAY, -1);

    }

    public long getCallbackDelayInMiliSec() {
        return mSharedPreferences.getLong(CALL_BACK_DELAY, -1);
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
