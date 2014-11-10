package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.content.Intent;

import com.easyback.andriy.eazyback.ui.activitys.CallPanelSettingsActivity;
import com.easyback.andriy.eazyback.ui.activitys.NumbersManagerActivity;

public final class ActivityLauncher {

    public static void launchNumbersActivity(Activity pActivity){
        Intent intent = new Intent(pActivity, NumbersManagerActivity.class);
        pActivity.startActivity(intent);
    }

    public static void launchCallPanelActivity(Activity pActivity){
        Intent intent = new Intent(pActivity, CallPanelSettingsActivity.class);
        pActivity.startActivity(intent);
    }

}
