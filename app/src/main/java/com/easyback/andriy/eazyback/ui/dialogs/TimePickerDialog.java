package com.easyback.andriy.eazyback.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.easyback.andriy.eazyback.R;

import java.util.Calendar;
import java.util.Date;

public final class TimePickerDialog extends GenericDialog {

    private static final int HOUR_COEFFICIENT = 3600;
    private static final int MINUTE_COEFFICIENT = 60;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.create_simple_plan_time_picker_title);
        return inflater.inflate(R.layout.dialog_time, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTimePicker = (TimePicker) view.findViewById(R.id.time_dialog_time_pick);
        view.findViewById(R.id.time_dialog_ok_button).setOnClickListener(new Clicker());
    }

    private boolean prepareSelectedTimeAndSend() {

        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        //TODO WTF with month ?
        calendar.set(year, month, day, hour, minute);

        long finalTime = calendar.getTimeInMillis() / 1000;
        Date date = calendar.getTime();

        long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime > finalTime) {
            showLongToast(R.string.wrong_data_time_error);
            return false;
        } else {
            mDialogsCallbacks.timeSelected(date, (int) finalTime);
            return true;
        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (prepareSelectedTimeAndSend()) {
                dismiss();
            }
        }
    }

}
