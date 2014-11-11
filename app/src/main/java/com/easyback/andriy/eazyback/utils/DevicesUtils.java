package com.easyback.andriy.eazyback.utils;

import android.content.Context;
import android.media.AudioManager;

public final class DevicesUtils {

    public static boolean isBluetoothHeadsetPlugged(Context pContext){
        AudioManager audioManager = (AudioManager) pContext.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isBluetoothA2dpOn();
    }

}
