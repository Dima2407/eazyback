package com.easyback.andriy.eazyback.core;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public final class DeviceService extends Service {

    private BroadcastReceiver mWiredHeadsetBroadcastReceiver, mBtHeadsetBroadcastReceiver;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("HsL", "Start service");

        registerBtEventListener();
        registerWiredEventListener();
    }

    private void registerWiredEventListener() {
        mWiredHeadsetBroadcastReceiver = new WiredHeadsetListener();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mWiredHeadsetBroadcastReceiver, intentFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HsL", "Start command");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean reviewConnectedBluetoothHeadset() {
        Log.e("HsL", "review bt connect");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Log.e("HsL", "null adapter");
            return false;
        }

        int bluetoothConnectionState = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);
        if (bluetoothConnectionState == BluetoothProfile.STATE_CONNECTED) {
            Log.e("HsL", " find connected device");
            return true;
        }

        return false;
    }

    private void registerBtEventListener() {
        Log.e("HsL", "register bt event");
        mBtHeadsetBroadcastReceiver = new BtHeadsetListener();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBtHeadsetBroadcastReceiver, intentFilter);
    }

    private void beginTranslatePluggedFlags() {
        SharedHelper sharedHelper = ((EzApplication) getApplicationContext()).getSharedHelper();

        if (sharedHelper.getPlugHeadsetIgnoreControl()) {
            Log.e("HsL", "ignore plug device");
            return;
        }

        Log.e("HsL", "begin translation, automat = "+sharedHelper.getPlugHeadsetAutomatControl());

        sharedHelper.setCallbackActivate(sharedHelper.getPlugHeadsetAutomatControl());
        sharedHelper.setActivateManualMode(sharedHelper.getPlugHeadsetManualControl());
    }

    private void beginTranslateUnPluggedFlags() {
        SharedHelper sharedHelper = ((EzApplication) getApplicationContext()).getSharedHelper();

        if (sharedHelper.getUnPlugHeadsetIgnoreControl()) {
            return;
        }

        sharedHelper.setCallbackActivate(sharedHelper.getUnPlugHeadsetAutomatControl());
        sharedHelper.setActivateManualMode(sharedHelper.getUnPlugHeadsetManualControl());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("HsL", "Destroy service");

        if (mWiredHeadsetBroadcastReceiver != null) {
            unregisterReceiver(mWiredHeadsetBroadcastReceiver);
            mWiredHeadsetBroadcastReceiver = null;
        }

        if (mBtHeadsetBroadcastReceiver != null) {
            unregisterReceiver(mBtHeadsetBroadcastReceiver);
            mBtHeadsetBroadcastReceiver = null;
        }
    }

    private final class WiredHeadsetListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case (0):

                        Log.e("HsL", "wired headset un plugged");
                        if (reviewConnectedBluetoothHeadset()) {
                            beginTranslatePluggedFlags();
                        }

                        break;

                    case (1):
                        Log.e("HsL", "wired headset plugged");
                        beginTranslatePluggedFlags();
                        break;

                    default:
                        Log.e("HsL", "wired headset error");
                }
            }
        }
    }

    private final class BtHeadsetListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("HsL", "income intent = " + intent.toString());

            if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                Log.e("HsL", "bt headset connected");
                beginTranslatePluggedFlags();
                return;
            }

            if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                Log.e("HsL", "bt headset dis connected");
                beginTranslateUnPluggedFlags();
            }

        }
    }
}
