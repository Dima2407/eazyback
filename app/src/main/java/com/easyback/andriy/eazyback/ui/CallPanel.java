package com.easyback.andriy.eazyback.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.easyback.andriy.eazyback.R;

public class CallPanel extends LinearLayout {

    private final OnClickListener mOnClickListener;
    private final LayoutInflater mLayoutInflater;

    public CallPanel(Context context, OnClickListener pOnClickListener) {
        super(context);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView();
    }

    public CallPanel(Context context, AttributeSet attrs, OnClickListener pOnClickListener) {
        super(context, attrs);
        mOnClickListener = pOnClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        View view = mLayoutInflater.inflate(R.layout.call_panlel_window, this);

        view.findViewById(R.id.accept_button).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.reject_button).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.callback_button).setOnClickListener(mOnClickListener);
    }
}
