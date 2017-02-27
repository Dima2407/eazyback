package com.kaa_solutions.eazyback.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.core.EzApplication;
import com.kaa_solutions.eazyback.core.SharedHelper;

public final class CallPanel extends FrameLayout {

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

        ImageView acceptBtn = (ImageView) findViewById(R.id.accept_image);
        LayoutParams paramsAcceptBtn = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        paramsAcceptBtn.setMargins(helper.getAcceptButtonMarginLeft(), helper.getAcceptButtonMarginTop(), 0, 0);
        acceptBtn.setLayoutParams(paramsAcceptBtn);

        if (pSharedHelper.getActivateAcceptButton()) {
            acceptBtn.setOnClickListener(mOnClickListener);
        } else {
            acceptBtn.setVisibility(GONE);
        }


        ImageView rejectBtn = (ImageView) findViewById(R.id.reject_image);
        LayoutParams paramsRejectBtn = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        paramsRejectBtn.setMargins(helper.getRejectButtonMarginLeft(), helper.getRejectButtonMarginTop(), 0, 0);
        rejectBtn.setLayoutParams(paramsRejectBtn);

        if (pSharedHelper.getActivateRejectButton()) {
            rejectBtn.setOnClickListener(mOnClickListener);
        } else {
            rejectBtn.setVisibility(GONE);
        }


        ImageView callbackBtn = (ImageView) findViewById(R.id.callback_image);
        LayoutParams paramsCallbackBtn = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        paramsCallbackBtn.setMargins(helper.getCallbackButtonMarginLeft(), helper.getCallbackButtonMarginTop(), 0, 0);
        callbackBtn.setLayoutParams(paramsCallbackBtn);

        if (pSharedHelper.getActivateCallbackButton()) {
            callbackBtn.setOnClickListener(mOnClickListener);
        } else {
            callbackBtn.setVisibility(GONE);
        }


        ImageView delayBtn = (ImageView) findViewById(R.id.delay_image);
        LayoutParams paramsDelayBtn = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        paramsDelayBtn.setMargins(helper.getDelayButtonMarginLeft(), helper.getDelayButtonMarginTop(), 0, 0);
        delayBtn.setLayoutParams(paramsDelayBtn);

        if (pSharedHelper.getActivateDelayCallbackButton()) {
            delayBtn.setOnClickListener(mOnClickListener);
        } else {
            delayBtn.setVisibility(GONE);
        }
    }
}
