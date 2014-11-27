package com.easyback.andriy.eazyback.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.easyback.andriy.eazyback.R;
import com.easyback.andriy.eazyback.core.EzApplication;
import com.easyback.andriy.eazyback.core.SharedHelper;

public final class CallPanel extends LinearLayout {

    private final OnClickListener mOnClickListener;
    private final LayoutInflater mLayoutInflater;

    public CallPanel(Context context, OnClickListener pOnClickListener) {
        super(context);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView(((EzApplication) context.getApplicationContext()).getSharedHelper());
    }

    public CallPanel(Context context, AttributeSet attrs, OnClickListener pOnClickListener) {
        super(context, attrs);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView(((EzApplication) context.getApplicationContext()).getSharedHelper());
    }

    private void initView(SharedHelper pSharedHelper) {
        View view = mLayoutInflater.inflate(R.layout.call_panlel_window, this);

        if (pSharedHelper.getActivateAcceptButton()) {
            view.findViewById(R.id.accept_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.accept_button).setVisibility(GONE);
        }

        if (pSharedHelper.getActivateRejectButton()) {
            view.findViewById(R.id.reject_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.reject_button).setVisibility(GONE);
        }

        if (pSharedHelper.getActivateCallbackButton()) {
            view.findViewById(R.id.callback_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.callback_button).setVisibility(GONE);
        }

        if (pSharedHelper.getActivateDelayCallbackButton()) {
            view.findViewById(R.id.delay_callback_button).setOnClickListener(mOnClickListener);
        } else {
            view.findViewById(R.id.delay_callback_button).setVisibility(GONE);
        }
    }
}
