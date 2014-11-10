package com.easyback.andriy.eazyback.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;

import java.util.List;

public class CallPanel extends LinearLayout {

    private final OnClickListener mOnClickListener;
    private final LayoutInflater mLayoutInflater;

    public CallPanel(Context context, OnClickListener pOnClickListener) {
        super(context);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView(((EzApplication) context.getApplicationContext()).getSharedHelper().getActivatedButtons());
    }

    public CallPanel(Context context, AttributeSet attrs, OnClickListener pOnClickListener) {
        super(context, attrs);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView(((EzApplication) context.getApplicationContext()).getSharedHelper().getActivatedButtons());
    }

    private void initView(List<Boolean> pActivatedStatus) {
        View view = mLayoutInflater.inflate(R.layout.call_panlel_window, this);

        if (pActivatedStatus.get(0)) {
            view.findViewById(R.id.accept_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.accept_button).setVisibility(GONE);
        }

        if (pActivatedStatus.get(1)) {
            view.findViewById(R.id.reject_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.reject_button).setVisibility(GONE);
        }

        if (pActivatedStatus.get(2)) {
            view.findViewById(R.id.callback_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.callback_button).setVisibility(GONE);
        }

        if (pActivatedStatus.get(3)) {
            view.findViewById(R.id.close_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.close_button).setVisibility(GONE);
        }
    }
}
