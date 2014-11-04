package com.easyback.andriy.eazyback.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.easyback.andriy.eazyback.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ViewInitUtils {

    public static List<EditText> initPhoneSells(View pView, Set<String> pPhones){
        List<EditText> editTexts = new ArrayList<EditText>(5);
        editTexts.add((EditText) pView.findViewById(R.id.number_one));
        editTexts.add((EditText) pView.findViewById(R.id.number_two));
        editTexts.add((EditText) pView.findViewById(R.id.number_there));
        editTexts.add((EditText) pView.findViewById(R.id.number_four));
        editTexts.add((EditText) pView.findViewById(R.id.number_five));

        String phone;
        for (int i = 0; i < pPhones.size(); i++) {
            phone = pPhones.get
            if(!TextUtils.isEmpty())
            editTexts.get(i).setText();
        }

    }


}
