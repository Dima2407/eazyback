package com.easyback.andriy.eazyback.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SharedHelper {

    private static final String NAME = "EazyBack";
    private static final String DONATE = "donate";

    private static final String ACTIVATE_FLAG = "activate_flag";
    private static final String ACTIVATE_MANUAL_FLAG = "activate_manual_flag";
    private static final String TARGET_PHONE_SET = "target_phone_set";
    private static final String REJECT_DELAY = "reject_delay";
    private static final String CALL_BACK_DELAY = "callback_delay";

    private static final String ACTIVATED_ACCEPT_BUTTON = "activated_accept_button";
    private static final String ACTIVATED_REJECT_BUTTON = "activated_reject_button";
    private static final String ACTIVATED_CALLBACK_BUTTON = "activated_callback_button";
    private static final String ACTIVATED_DELAY_CALLBACK_BUTTON = "activated_delay_callback_button";

    private static final String HEADSET_PLUG_MAIN_CONTROL = "headset_plug_main_control";

    private static final String HEADSET_PLUG_AUTOMAT = "headset_plug_automat";
    private static final String HEADSET_PLUG_MANUAL = "headset_plug_manual";
    private static final String HEADSET_PLUG_IGNORE = "headset_plug_ignore";

    private static final String HEADSET_UN_PLUG_AUTOMAT = "headset_un_plug_automat";
    private static final String HEADSET_UN_PLUG_MANUAL = "headset_un_plug_manual";
    private static final String HEADSET_UN_PLUG_IGNORE = "headset_un_plug_ignore";

    private static final String DELAY_CALLBACK_NUMBERS = "delay_callback_numbers";
    private static final String DELAY_BUTTONS_WINDOW = "delay_buttons_window";
    private static final String MANUAL_INTERCEPT_MODE = "manual_intercept_mode";

    private static final String FLOAT_WINDOW_X = "float_window_x";
    private static final String FLOAT_WINDOW_Y = "float_window_y";

    private final SharedPreferences mSharedPreferences;

    public SharedHelper(Context pContext) {
        mSharedPreferences = pContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setCallbackActivate(boolean pIsActivate) {
        mSharedPreferences.edit().putBoolean(ACTIVATE_FLAG, pIsActivate).commit();
    }

    public boolean getIsCallbacksActivate() {
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

    public void setActivatedButtons(List<Boolean> pActivatedButtons) {
        mSharedPreferences.edit().putBoolean(ACTIVATED_ACCEPT_BUTTON, pActivatedButtons.get(0)).commit();
        mSharedPreferences.edit().putBoolean(ACTIVATED_REJECT_BUTTON, pActivatedButtons.get(1)).commit();
        mSharedPreferences.edit().putBoolean(ACTIVATED_CALLBACK_BUTTON, pActivatedButtons.get(2)).commit();
        mSharedPreferences.edit().putBoolean(ACTIVATED_DELAY_CALLBACK_BUTTON, pActivatedButtons.get(3)).commit();
    }

    public List<Boolean> getActivatedButtons() {
        List<Boolean> activatedButtons = new ArrayList<Boolean>(4);
        activatedButtons.add(mSharedPreferences.getBoolean(ACTIVATED_ACCEPT_BUTTON, true));
        activatedButtons.add(mSharedPreferences.getBoolean(ACTIVATED_REJECT_BUTTON, true));
        activatedButtons.add(mSharedPreferences.getBoolean(ACTIVATED_CALLBACK_BUTTON, true));
        activatedButtons.add(mSharedPreferences.getBoolean(ACTIVATED_DELAY_CALLBACK_BUTTON, true));

        return activatedButtons;
    }

    public void setDeviceActive(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_MAIN_CONTROL, pActivated).commit();
    }

    public boolean getIsActivatedDeviceControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_MAIN_CONTROL, false);
    }

    public void setPlugHeadsetAutomatControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_AUTOMAT, pActive).commit();
    }

    public boolean getPlugHeadsetAutomatControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_AUTOMAT, false);
    }

    public void setPlugHeadsetManualControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_MANUAL, pActive).commit();
    }

    public boolean getPlugHeadsetManualControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_MANUAL, false);
    }

    public void setPlugHeadsetIgnoreControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_IGNORE, pActive).commit();
    }

    public boolean getPlugHeadsetIgnoreControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_IGNORE, true);
    }

    public void setUnPlugHeadsetAutomatControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_AUTOMAT, pActive).commit();
    }

    public boolean getUnPlugHeadsetAutomatControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_AUTOMAT, false);
    }

    public void setUnPlugHeadsetManualControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_MANUAL, pActive).commit();
    }

    public boolean getUnPlugHeadsetManualControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_MANUAL, false);
    }

    public void setUnPlugHeadsetIgnoreControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_IGNORE, pActive).commit();
    }

    public boolean getUnPlugHeadsetIgnoreControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_IGNORE, true);
    }

    public Set<String> getDelayCallbackNumbers() {
        return mSharedPreferences.getStringSet(DELAY_CALLBACK_NUMBERS, new HashSet<String>());
    }

    public void addDelayCallbackNumber(String pDelayCallbackNumber) {
        Set<String> phoneSet = getDelayCallbackNumbers();
        phoneSet.add(pDelayCallbackNumber);
        mSharedPreferences.edit().putStringSet(DELAY_CALLBACK_NUMBERS, phoneSet).commit();
    }

    public void removeDelayCallbackNumber(String pDelayCallbackNumber) {
        Set<String> phoneSet = getDelayCallbackNumbers();
        phoneSet.remove(pDelayCallbackNumber);
        mSharedPreferences.edit().putStringSet(DELAY_CALLBACK_NUMBERS, phoneSet).commit();
    }

    public long getButtonsDelayInSec() {
        long delay = mSharedPreferences.getLong(DELAY_BUTTONS_WINDOW, 0);

        if (delay != 0) {
            return delay / 1000;
        }
        return delay;
    }

    public long getButtonsDelayInMiliSec() {
        return mSharedPreferences.getLong(DELAY_BUTTONS_WINDOW, 0);
    }

    public void setButtonDelay(String pButtonsDelay) {
        if (!TextUtils.isEmpty(pButtonsDelay)) {
            long reject = Long.parseLong(pButtonsDelay) * 1000;
            mSharedPreferences.edit().putLong(DELAY_BUTTONS_WINDOW, reject).commit();
        }
    }

    public void setManualInterceptMode(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(MANUAL_INTERCEPT_MODE, pActivated).commit();
    }

    public boolean getManualInterceptMode() {
        return mSharedPreferences.getBoolean(MANUAL_INTERCEPT_MODE, false);
    }

    public void setFloatWindowX(int pFloatWindowX) {
        mSharedPreferences.edit().putInt(FLOAT_WINDOW_X, pFloatWindowX).commit();
    }

    public void setFloatWindowY(int pFloatWindowY) {
        mSharedPreferences.edit().putInt(FLOAT_WINDOW_Y, pFloatWindowY).commit();
    }

    public int getFloatWindowX() {
        return mSharedPreferences.getInt(FLOAT_WINDOW_X, 0);
    }

    public int getFloatWindowY() {
        return mSharedPreferences.getInt(FLOAT_WINDOW_Y, 0);
    }

    public void setDonate(boolean pIsDonateComplete){
        mSharedPreferences.edit().putBoolean(DONATE, pIsDonateComplete).commit();
    }

    public boolean getDonate(){
        return mSharedPreferences.getBoolean(DONATE, false);
    }
}
