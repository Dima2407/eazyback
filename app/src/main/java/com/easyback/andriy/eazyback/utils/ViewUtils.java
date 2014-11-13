package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
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
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,

                //WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                //WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,

                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);

// Define the position of the window within the screen
        p.gravity = Gravity.RIGHT;

        CallPanel controlPanel = new CallPanel(pContext, pOnClickListener);
        WindowManager windowManager = (WindowManager) pContext.getSystemService(Activity.WINDOW_SERVICE);
        windowManager.addView(controlPanel, p);
        return controlPanel;
    }

    public static void hideInterceptWindow(Context pContext, CallPanel pCallPanel) {
        WindowManager windowManager = (WindowManager) pContext.getSystemService(Activity.WINDOW_SERVICE);
        windowManager.removeViewImmediate(pCallPanel);

    }

}
