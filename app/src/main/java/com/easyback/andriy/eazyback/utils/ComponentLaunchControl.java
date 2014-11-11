package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.easyback.andriy.eazyback.core.DeviceService;
import com.easyback.andriy.eazyback.ui.activities.CallPanelSettingsActivity;
import com.easyback.andriy.eazyback.ui.activities.DeviceManagerActivity;
import com.easyback.andriy.eazyback.ui.activities.NumbersManagerActivity;

public final class ComponentLaunchControl {

    public static void launchNumbersActivity(Activity pActivity){
        Intent intent = new Intent(pActivity, NumbersManagerActivity.class);
        pActivity.startActivity(intent);
    }

    public static void launchCallPanelActivity(Activity pActivity){
        Intent intent = new Intent(pActivity, CallPanelSettingsActivity.class);
        pActivity.startActivity(intent);
    }

    public static void launchDevicesActivity(Activity pActivity){
        Intent intent = new Intent(pActivity, DeviceManagerActivity.class);
        pActivity.startActivity(intent);
    }

    public static void startDeviceService(Context pContext){
        Intent intent = new Intent(pContext, DeviceService.class);
        pContext.startService(intent);
    }

    public static void stopDeviceService(Context pContext){
        Intent intent = new Intent(pContext, DeviceService.class);
        pContext.stopService(intent);
    }

}
