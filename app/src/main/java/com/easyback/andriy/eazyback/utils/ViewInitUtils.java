package com.easyback.andriy.eazyback.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.SharedHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ViewInitUtils {

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
        for (EditText editText : pEditTexts) {
            if(!TextUtils.isEmpty(editText.getText().toString())){
                stringSet.add(editText.getText().toString());
            }
        }

        pSharedHelper.setTargetPhoneSet(stringSet);
    }
}
