package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;
import com.easyback.andriy.eazyback.ui.CallPanel;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ViewUtils {

    public static List<EditText> initPhoneSells(Activity pView, Set<String> pPhones) {
        List<EditText> editTexts = new ArrayList<EditText>(5);
        editTexts.add((EditText) pView.findViewById(R.id.number_one));
        editTexts.add((EditText) pView.findViewById(R.id.number_two));
        editTexts.add((EditText) pView.findViewById(R.id.number_three));
        editTexts.add((EditText) pView.findViewById(R.id.number_four));
        editTexts.add((EditText) pView.findViewById(R.id.number_five));

        List<String> phoneList = new ArrayList<String>(pPhones);
        for (int i = 0; i < pPhones.size(); i++) {
            editTexts.get(i).setText(phoneList.get(i));
        }
        return editTexts;
    }

    public static List<EditText> initPhoneSellsExtended(Activity pView, Set<String> pPhones) {
        List<EditText> editTexts = new ArrayList<EditText>(10);
        editTexts.add((EditText) pView.findViewById(R.id.number_one));
        editTexts.add((EditText) pView.findViewById(R.id.number_two));
        editTexts.add((EditText) pView.findViewById(R.id.number_three));
        editTexts.add((EditText) pView.findViewById(R.id.number_four));
        editTexts.add((EditText) pView.findViewById(R.id.number_five));
        editTexts.add((EditText) pView.findViewById(R.id.number_six));
        editTexts.add((EditText) pView.findViewById(R.id.number_seven));
        editTexts.add((EditText) pView.findViewById(R.id.number_eight));
        editTexts.add((EditText) pView.findViewById(R.id.number_nine));
        editTexts.add((EditText) pView.findViewById(R.id.number_ten));

        List<String> phoneList = new ArrayList<String>(pPhones);
        for (int i = 0; i < pPhones.size(); i++) {
            editTexts.get(i).setText(phoneList.get(i));
        }

        return editTexts;
    }

    public static List<EditText> initPhoneSellsUltra(Activity pView, Set<String> pPhones) {
        List<EditText> editTexts = new ArrayList<EditText>(20);
        editTexts.add((EditText) pView.findViewById(R.id.number_one));
        editTexts.add((EditText) pView.findViewById(R.id.number_two));
        editTexts.add((EditText) pView.findViewById(R.id.number_three));
        editTexts.add((EditText) pView.findViewById(R.id.number_four));
        editTexts.add((EditText) pView.findViewById(R.id.number_five));
        editTexts.add((EditText) pView.findViewById(R.id.number_six));
        editTexts.add((EditText) pView.findViewById(R.id.number_seven));
        editTexts.add((EditText) pView.findViewById(R.id.number_eight));
        editTexts.add((EditText) pView.findViewById(R.id.number_nine));
        editTexts.add((EditText) pView.findViewById(R.id.number_ten));
        editTexts.add((EditText) pView.findViewById(R.id.number_eleven));
        editTexts.add((EditText) pView.findViewById(R.id.number_twelve));
        editTexts.add((EditText) pView.findViewById(R.id.number_thirteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_fourteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_fifteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_sixteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_seventeen));
        editTexts.add((EditText) pView.findViewById(R.id.number_eighteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_nineteen));
        editTexts.add((EditText) pView.findViewById(R.id.number_twenty));

        List<String> phoneList = new ArrayList<String>(pPhones);
        for (int i = 0; i < pPhones.size(); i++) {
            editTexts.get(i).setText(phoneList.get(i));
        }

        return editTexts;
    }

    public static void savePhoneCells(List<EditText> pEditTexts, SharedHelper pSharedHelper) {
        Set<String> stringSet = new HashSet<String>();
        String phone;
        for (EditText editText : pEditTexts) {
            phone = editText.getText().toString();
            if (!TextUtils.isEmpty(phone)) {
                stringSet.add(phone);
            }
        }

        pSharedHelper.setTargetPhoneSet(stringSet);
    }

    public static CallPanel showInterceptWindow(Context pContext, View.OnClickListener pOnClickListener) {

        EasyTracker.getInstance(pContext).set(Fields.EVENT_ACTION, "Show call panel");

        SharedHelper sharedHelper = ((EzApplication) pContext.getApplicationContext()).getSharedHelper();

        final WindowManager manager = (WindowManager) pContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.TRANSLUCENT;

        calculatePositionFloatWindow(sharedHelper, pContext, layoutParams);

        layoutParams.packageName = pContext.getPackageName();
        layoutParams.windowAnimations = android.R.style.Animation_Dialog;

        CallPanel controlPanel = new CallPanel(pContext, pOnClickListener);
        manager.addView(controlPanel, layoutParams);
        return controlPanel;
    }

    public static void hideInterceptWindow(Context pContext, CallPanel pCallPanel) {
        WindowManager windowManager = (WindowManager) pContext.getSystemService(Activity.WINDOW_SERVICE);
        windowManager.removeView(pCallPanel);
    }

    private static void calculatePositionFloatWindow(SharedHelper pSharedHelper, Context pContext, WindowManager.LayoutParams pLayoutParams) {

        int stockX = pSharedHelper.getFloatWindowX();
        int stockY = pSharedHelper.getFloatWindowY();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int halfScreenWidth = displayMetrics.widthPixels / 2;
        int halfScreenHeight = displayMetrics.heightPixels / 2;

        if ((stockX - halfScreenWidth) > 0) {
            pLayoutParams.x = stockX / 2;
        } else {
            pLayoutParams.x = stockX - halfScreenWidth;
        }

        if ((stockY - halfScreenHeight) > 0) {
            pLayoutParams.y = stockY / 2;
        } else {
            pLayoutParams.y = stockY - halfScreenHeight;
        }
    }

}
