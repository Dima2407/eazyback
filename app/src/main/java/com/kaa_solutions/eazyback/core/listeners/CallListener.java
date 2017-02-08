package com.kaa_solutions.eazyback.core.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.kaa_solutions.eazyback.core.Core;
import com.kaa_solutions.eazyback.core.EzApplication;

public final class CallListener extends BroadcastReceiver {

    private static final String TARGET_ACTION = "android.intent.action.PHONE_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(TARGET_ACTION)) {
            return;
        }

        String incomingPhoneNumber;
        String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        Core core = ((EzApplication) context.getApplicationContext()).getCore();
        if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            incomingPhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            core.makeParse(incomingPhoneNumber);
            return;
        }

        if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            core.hideCallPanelWindow();
            return;
        }
    }
}
