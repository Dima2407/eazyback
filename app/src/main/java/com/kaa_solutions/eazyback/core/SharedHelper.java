package com.kaa_solutions.eazyback.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

public final class SharedHelper {

    public static final int AMOUNT_PHONES_NUMBER = 5;
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
    private static final String AUTO_HIDE_CALL_PANEL = "auto_hide_call_panel";
    private static final String ACCEPT_BUTTON_MARGIN_LEFT = "accept_button_margin_left";
    private static final String ACCEPT_BUTTON_MARGIN_TOP = "accept_button_margin_top";
    private static final String REJECT_BUTTON_MARGIN_LEFT = "reject_button_margin_left";
    private static final String REJECT_BUTTON_MARGIN_TOP = "reject_button_margin_top";
    private static final String DELAY_BUTTON_MARGIN_LEFT = "delay_btn_margin_left";
    private static final String DELAY_BUTTON_MARGIN_TOP = "delay_btn_margin_top";
    private static final String CALLBACK_BUTTON_MARGIN_TOP = "callback_button_margin_top";
    private static final String CALLBACK_BUTTON_MARGIN_LEFT = "callback_button_margin_left";
    private static final int ACCEPT_BUTTON_MARGIN_TOP_DEFAULT = 0;
    private static final int REJECT_BUTTON_MARGIN_TOP_DEFAULT = 80;
    private static final int DELAY_BUTTON_MARGIN_TOP_DEFAULT = 160;
    private static final int CALLBACK_BUTTON_MARGIN_TOP_DEFAULT = 240;

    private final SharedPreferences mSharedPreferences;

    public SharedHelper(Context pContext) {
        mSharedPreferences = pContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    private static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public int getAcceptButtonMarginTop() {
        return mSharedPreferences.getInt(ACCEPT_BUTTON_MARGIN_TOP, dpToPx(ACCEPT_BUTTON_MARGIN_TOP_DEFAULT));
    }

    public void setAcceptButtonMarginTop(int value) {
        mSharedPreferences.edit().putInt(ACCEPT_BUTTON_MARGIN_TOP, value).commit();
    }

    public int getAcceptButtonMarginLeft() {
        return mSharedPreferences.getInt(ACCEPT_BUTTON_MARGIN_LEFT, 0);
    }

    public void setAcceptButtonMarginLeft(int value) {
        mSharedPreferences.edit().putInt(ACCEPT_BUTTON_MARGIN_LEFT, value).commit();
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
        long delay = mSharedPreferences.getLong(REJECT_DELAY, 3000);

        if (delay != -1) {
            return delay / 1000;
        }


        return delay;
    }

    public long getCallbackDelayInSec() {
        long delay = mSharedPreferences.getLong(CALL_BACK_DELAY, 3000);

        if (delay != -1) {
            return delay / 1000;
        }

        return delay;
    }

    public long getRejectDelayInMilSec() {
        return mSharedPreferences.getLong(REJECT_DELAY, -1);

    }

    public long getCallbackDelayInMilSec() {
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

    public boolean getActivateAcceptButton() {
        return mSharedPreferences.getBoolean(ACTIVATED_ACCEPT_BUTTON, true);
    }

    public void setActivateAcceptButton(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(ACTIVATED_ACCEPT_BUTTON, pActivated).commit();
    }

    public boolean getActivateRejectButton() {
        return mSharedPreferences.getBoolean(ACTIVATED_REJECT_BUTTON, true);
    }

    public void setActivateRejectButton(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(ACTIVATED_REJECT_BUTTON, pActivated).commit();
    }

    public boolean getActivateCallbackButton() {
        return mSharedPreferences.getBoolean(ACTIVATED_CALLBACK_BUTTON, true);
    }

    public void setActivateCallbackButton(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(ACTIVATED_CALLBACK_BUTTON, pActivated).commit();
    }

    public boolean getActivateDelayCallbackButton() {
        return mSharedPreferences.getBoolean(ACTIVATED_DELAY_CALLBACK_BUTTON, true);
    }

    public void setActivateDelayCallbackButton(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(ACTIVATED_DELAY_CALLBACK_BUTTON, pActivated).commit();
    }

    public void setDeviceActive(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_MAIN_CONTROL, pActivated).commit();
    }

    public boolean getIsActivatedDeviceControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_MAIN_CONTROL, false);
    }

    public boolean getPlugHeadsetAutomatControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_AUTOMAT, false);
    }

    public void setPlugHeadsetAutomatControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_AUTOMAT, pActive).commit();
    }

    public boolean getPlugHeadsetManualControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_MANUAL, false);
    }

    public void setPlugHeadsetManualControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_MANUAL, pActive).commit();
    }

    public boolean getPlugHeadsetIgnoreControl() {
        return mSharedPreferences.getBoolean(HEADSET_PLUG_IGNORE, true);
    }

    public void setPlugHeadsetIgnoreControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_PLUG_IGNORE, pActive).commit();
    }

    public boolean getUnPlugHeadsetAutomatControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_AUTOMAT, false);
    }

    public void setUnPlugHeadsetAutomatControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_AUTOMAT, pActive).commit();
    }

    public boolean getUnPlugHeadsetManualControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_MANUAL, false);
    }

    public void setUnPlugHeadsetManualControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_MANUAL, pActive).commit();
    }

    public boolean getUnPlugHeadsetIgnoreControl() {
        return mSharedPreferences.getBoolean(HEADSET_UN_PLUG_IGNORE, true);
    }

    public void setUnPlugHeadsetIgnoreControl(boolean pActive) {
        mSharedPreferences.edit().putBoolean(HEADSET_UN_PLUG_IGNORE, pActive).commit();
    }

    public long getButtonsDelayInSec() {
        long delay = mSharedPreferences.getLong(DELAY_BUTTONS_WINDOW, 0);

        if (delay != 0) {
            return delay / 1000;
        }
        return delay;
    }

    public long getButtonsDelayInMilSec() {
        return mSharedPreferences.getLong(DELAY_BUTTONS_WINDOW, 0);
    }

    public void setButtonDelay(String pButtonsDelay) {
        if (!TextUtils.isEmpty(pButtonsDelay)) {
            long reject = Long.parseLong(pButtonsDelay) * 1000;
            mSharedPreferences.edit().putLong(DELAY_BUTTONS_WINDOW, reject).commit();
        }
    }

    public boolean getManualInterceptMode() {
        return mSharedPreferences.getBoolean(MANUAL_INTERCEPT_MODE, false);
    }

    public void setManualInterceptMode(boolean pActivated) {
        mSharedPreferences.edit().putBoolean(MANUAL_INTERCEPT_MODE, pActivated).commit();
    }

    public int getFloatWindowX() {
        return mSharedPreferences.getInt(FLOAT_WINDOW_X, 0);
    }

    public void setFloatWindowX(int pFloatWindowX) {
        mSharedPreferences.edit().putInt(FLOAT_WINDOW_X, pFloatWindowX).commit();
    }

    public int getFloatWindowY() {
        return mSharedPreferences.getInt(FLOAT_WINDOW_Y, 0);
    }

    public void setFloatWindowY(int pFloatWindowY) {
        mSharedPreferences.edit().putInt(FLOAT_WINDOW_Y, pFloatWindowY).commit();
    }

    public int getDonate() {
        return mSharedPreferences.getInt(DONATE, -1);
    }

    public void setDonate(int pDonateCompleteCode) {
        mSharedPreferences.edit().putInt(DONATE, pDonateCompleteCode).commit();
    }

    public boolean getAutoHideCallPanel() {
        return mSharedPreferences.getBoolean(AUTO_HIDE_CALL_PANEL, true);
    }

    public void setAutoHideCallPanel(boolean pHideCallPanel) {
        mSharedPreferences.edit().putBoolean(AUTO_HIDE_CALL_PANEL, pHideCallPanel).commit();
    }

    public int getRejectButtonMarginLeft() {
        return mSharedPreferences.getInt(REJECT_BUTTON_MARGIN_LEFT, 0);
    }

    public void setRejectButtonMarginLeft(int value) {
        mSharedPreferences.edit().putInt(REJECT_BUTTON_MARGIN_LEFT, value).commit();
    }

    public int getRejectButtonMarginTop() {
        return mSharedPreferences.getInt(REJECT_BUTTON_MARGIN_TOP, dpToPx(REJECT_BUTTON_MARGIN_TOP_DEFAULT));
    }

    public void setRejectButtonMarginTop(int value) {
        mSharedPreferences.edit().putInt(REJECT_BUTTON_MARGIN_TOP, value).commit();
    }

    public int getCallbackButtonMarginTop() {
        return mSharedPreferences.getInt(CALLBACK_BUTTON_MARGIN_TOP, dpToPx(CALLBACK_BUTTON_MARGIN_TOP_DEFAULT));
    }

    public void setCallbackButtonMarginTop(int value) {
        mSharedPreferences.edit().putInt(CALLBACK_BUTTON_MARGIN_TOP, value).commit();
    }

    public int getCallbackButtonMarginLeft() {
        return mSharedPreferences.getInt(CALLBACK_BUTTON_MARGIN_LEFT, 0);
    }

    public void setCallbackButtonMarginLeft(int value) {
        mSharedPreferences.edit().putInt(CALLBACK_BUTTON_MARGIN_LEFT, value).commit();
    }


    public int getDelayButtonMarginLeft() {
        return mSharedPreferences.getInt(DELAY_BUTTON_MARGIN_LEFT, 0);
    }

    public void setDelayButtonMarginLeft(int value) {
        mSharedPreferences.edit().putInt(DELAY_BUTTON_MARGIN_LEFT, value).commit();
    }


    public int getDelayButtonMarginTop() {
        return mSharedPreferences.getInt(DELAY_BUTTON_MARGIN_TOP, dpToPx(DELAY_BUTTON_MARGIN_TOP_DEFAULT));
    }

    public void setDelayButtonMarginTop(int value) {
        mSharedPreferences.edit().putInt(DELAY_BUTTON_MARGIN_TOP, value).commit();
    }

}
