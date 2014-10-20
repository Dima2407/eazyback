package com.easyback.andriy.eazyback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.easyback.andriy.eazyback.core.EzApplication;

public final class CallListener extends BroadcastReceiver {

    private static final String TARGET_ACTION = "android.intent.action.PHONE_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!intent.getAction().equals(TARGET_ACTION)) {
            return;
        }

        String incomingPhoneNumber = null;
        String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            incomingPhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            ((EzApplication) context.getApplicationContext()).getCore().makeParse(incomingPhoneNumber);
        }


    }
}
