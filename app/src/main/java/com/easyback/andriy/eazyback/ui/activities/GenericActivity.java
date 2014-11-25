package com.easyback.andriy.eazyback.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.vending.billing.IInAppBillingService;
import com.easyback.andriy.eazyback.core.Core;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class GenericActivity extends Activity {

    private IInAppBillingService serviceBilling;

    private EzApplication mEzApplication;
    private SharedHelper mSharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEzApplication = (EzApplication) getApplication();

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    protected void setStatTag(String pScreenName) {
        EasyTracker.getInstance(this).set(Fields.SCREEN_NAME, pScreenName);
        EasyTracker.getInstance(this).send(MapBuilder.createAppView().build());
        EasyTracker.getInstance(this).activityStart(this);
    }

    protected SharedHelper getSharedHelper() {
        if (mSharedHelper == null) {
            mSharedHelper = mEzApplication.getSharedHelper();
        }
        return mSharedHelper;
    }

    protected Core getCore() {
        return mEzApplication.getCore();
    }

    protected void initBackButton() {
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void hideKeyboard(View pView) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                GenericActivity.this.finish();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBilling != null) {
            unbindService(serviceConnection);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBilling = IInAppBillingService.Stub.asInterface(service);

            ArrayList<String> skuList = new ArrayList<String>();
            skuList.add("premiumUpgrade");
            skuList.add("donation");
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
            try {
                Bundle skuDetails = serviceBilling.getSkuDetails(3,
                        getPackageName(), "inapp", querySkus);
                int response = skuDetails.getInt("RESPONSE_CODE");

                if (response == 0) {
                    ArrayList<String> responseList
                            = skuDetails.getStringArrayList("DETAILS_LIST");

                    for (String thisResponse : responseList) {
                        JSONObject object = new JSONObject(thisResponse);
                        String sku = object.getString("productId");
                        String price = object.getString("price");

                        Log.e("rest", " "+price + " "+sku + " "+object.toString());
                        Bundle b = serviceBilling.getBuyIntent(3, getPackageName(),sku,"inapp",null);
                        PendingIntent pendingIntent = b.getParcelable("BUY_INTENT");
                        startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                                Integer.valueOf(0));
                    }
                }

                Log.e("rest", " "+response);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBilling = null;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");

                }
                catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
