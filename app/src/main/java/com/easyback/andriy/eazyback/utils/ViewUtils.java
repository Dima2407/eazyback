package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;
import com.easyback.andriy.eazyback.ui.CallPanel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ViewUtils {

    public static List<EditText> initPhoneSells(Activity pView, Set<String> pPhones) {
        List<EditText> editTexts = new ArrayList<EditText>(5);
        editTexts.add((EditText) pView.findViewById(R.id.number_one));
        editTexts.add((EditText) pView.findViewById(R.id.number_two));
        editTexts.add((EditText) pView.findViewById(R.id.number_there));
        editTexts.add((EditText) pView.findViewById(R.id.number_four));
        editTexts.add((EditText) pView.findViewById(R.id.number_five));

        int i = 0;
        for (String phone : pPhones) {
            if (!TextUtils.isEmpty(phone)) {
                editTexts.get(i).setText(phone);
            }
            i++;
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

        SharedHelper sharedHelper = ((EzApplication) pContext.getApplicationContext()).getSharedHelper();

        final WindowManager manager = (WindowManager) pContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.TRANSLUCENT;

        Log.e("VU", "x = "+sharedHelper.getFloatWindowX());
        Log.e("VU", "y = "+sharedHelper.getFloatWindowY());

        layoutParams.x = 0;
        layoutParams.y = 0;

        layoutParams.packageName = pContext.getPackageName();
        layoutParams.windowAnimations = android.R.style.Animation_Dialog;

        CallPanel controlPanel = new CallPanel(pContext, pOnClickListener);

//        controlPanel.setX(sharedHelper.getFloatWindowX());
//        controlPanel.setY(sharedHelper.getFloatWindowY());

        manager.addView(controlPanel, layoutParams);
        return controlPanel;
    }

    public static void hideInterceptWindow(Context pContext, CallPanel pCallPanel) {
        WindowManager windowManager = (WindowManager) pContext.getSystemService(Activity.WINDOW_SERVICE);
        windowManager.removeViewImmediate(pCallPanel);

    }

}
