package com.kaa_solutions.eazyback.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.core.EzApplication;
import com.kaa_solutions.eazyback.core.SharedHelper;

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
        mLayoutInflater.inflate(R.layout.activity_floated_buttons, this);

        SharedHelper helper = new SharedHelper(getContext());

        RelativeLayout acceptBtn = (RelativeLayout) findViewById(R.id.layout_accept);
        acceptBtn.setPadding(helper.getAcceptButtonMarginLeft(), helper.getAcceptButtonMarginTop(), 0, 0);

        if (pSharedHelper.getActivateAcceptButton()) {
            acceptBtn.setOnClickListener(mOnClickListener);
        } else {
            acceptBtn.setVisibility(GONE);
        }


        RelativeLayout rejectBtn = (RelativeLayout) findViewById(R.id.layout_reject);
        rejectBtn.setPadding(helper.getRejectButtonMarginLeft(), helper.getRejectButtonMarginTop(), 0, 0);

        if (pSharedHelper.getActivateRejectButton()) {
            rejectBtn.setOnClickListener(mOnClickListener);
        } else {
            rejectBtn.setVisibility(GONE);
        }


        ImageView callbackBtn = (ImageView) findViewById(R.id.callback_image);
        callbackBtn.setPadding(helper.getCallbackButtonMarginLeft(), helper.getCallbackButtonMarginTop(), 0, 0);

        if (pSharedHelper.getActivateCallbackButton()) {
            callbackBtn.setOnClickListener(mOnClickListener);
        } else {
            callbackBtn.setVisibility(GONE);
        }

        ImageView delayBtn = (ImageView) findViewById(R.id.delay_image);
        delayBtn.setPadding(helper.getDelayButtonMarginLeft(), helper.getDelayButtonMarginTop(), 0, 0);

        if (pSharedHelper.getActivateDelayCallbackButton()) {
            delayBtn.setOnClickListener(mOnClickListener);
        } else {
            delayBtn.setVisibility(GONE);
        }
    }
}
