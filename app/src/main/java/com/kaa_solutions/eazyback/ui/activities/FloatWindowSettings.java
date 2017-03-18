package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.core.SharedHelper;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public final class FloatWindowSettings extends GenericActivity {

    public static final int RIGHT_MARGIN = -250;
    public static final int BOTTOM_MARGIN = -250;
    ImageView acceptBtn, rejectBtn, delayBtn, callbackBtn;
    private TextView textView;
    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_float_settings);
        setContentView(R.layout.activity_floated_buttons);
        defineImages();
        setButtonsMargin();
        textView = (TextView) findViewById(R.id.text_instruct);
        checkVisibilityButtons();
        initBackButton();
    }

    private void defineImages() {
        acceptBtn = (ImageView) findViewById(R.id.accept_image);
        rejectBtn = (ImageView) findViewById(R.id.reject_image);
        delayBtn = (ImageView) findViewById(R.id.delay_image);
        callbackBtn = (ImageView) findViewById(R.id.callback_image);
    }

    private void checkVisibilityButtons() {
        if (getSharedHelper().getActivateAcceptButton()) {
            acceptBtn.setVisibility(View.VISIBLE);
        } else {
            acceptBtn.setVisibility(View.GONE);
        }

        if (getSharedHelper().getActivateRejectButton()) {
            rejectBtn.setVisibility(View.VISIBLE);
        } else {
            rejectBtn.setVisibility(View.GONE);
        }

        if (getSharedHelper().getActivateDelayCallbackButton()) {
            delayBtn.setVisibility(View.VISIBLE);
        } else {
            delayBtn.setVisibility(View.GONE);
        }

        if (getSharedHelper().getActivateCallbackButton()) {
            callbackBtn.setVisibility(View.VISIBLE);
        } else {
            callbackBtn.setVisibility(View.GONE);
        }
    }

    private void setButtonsMargin() {
        SharedHelper helper = getSharedHelper();
        View.OnTouchListener onTouch = new OnTouch();
        FrameLayout.LayoutParams paramsAcceptBtn = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        paramsAcceptBtn.setMargins(helper.getAcceptButtonMarginLeft(), helper.getAcceptButtonMarginTop(), 0, 0);
        acceptBtn.setLayoutParams(paramsAcceptBtn);
        acceptBtn.setOnTouchListener(onTouch);

        FrameLayout.LayoutParams paramsRejectBtn = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        paramsRejectBtn.setMargins(helper.getRejectButtonMarginLeft(), helper.getRejectButtonMarginTop(), 0, 0);
        rejectBtn.setLayoutParams(paramsRejectBtn);
        rejectBtn.setOnTouchListener(onTouch);

        FrameLayout.LayoutParams paramsDelayBtn = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        paramsDelayBtn.setMargins(helper.getDelayButtonMarginLeft(), helper.getDelayButtonMarginTop(), 0, 0);
        delayBtn.setLayoutParams(paramsDelayBtn);
        delayBtn.setOnTouchListener(onTouch);

        FrameLayout.LayoutParams paramsCallbackBtn = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        paramsCallbackBtn.setMargins(helper.getCallbackButtonMarginLeft(), helper.getCallbackButtonMarginTop(), 0, 0);
        callbackBtn.setLayoutParams(paramsCallbackBtn);
        callbackBtn.setOnTouchListener(onTouch);
    }

    private class OnTouch implements View.OnTouchListener {

        private final String TAG = OnTouch.class.getSimpleName();

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (textView.getVisibility() == View.VISIBLE) {
                textView.setVisibility(View.INVISIBLE);
            }
            Log.e(getClass().getSimpleName(), "view Id: " + view.getId());
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;

                case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = RIGHT_MARGIN;
                    layoutParams.bottomMargin = BOTTOM_MARGIN;
                    view.setLayoutParams(layoutParams);

                    switch (view.getId()) {
                        case R.id.accept_image:
                            Log.e(TAG, "move : accept_image");
                            getSharedHelper().setAcceptButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setAcceptButtonMarginTop(layoutParams.topMargin);
                            break;

                        case R.id.reject_image:
                            Log.e(TAG, "move : reject_image");
                            getSharedHelper().setRejectButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setRejectButtonMarginTop(layoutParams.topMargin);
                            break;

                        case R.id.delay_image:
                            Log.e(TAG, "move : delay_image");
                            getSharedHelper().setDelayButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setDelayButtonMarginTop(layoutParams.topMargin);
                            break;

                        case R.id.callback_image:
                            Log.e(TAG, "move : callback_image");
                            getSharedHelper().setCallbackButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setCallbackButtonMarginTop(layoutParams.topMargin);
                            break;

                        default:
                            Log.e(TAG, "onTouch: Another ID");
                            break;
                    }
            }
            return true;
        }
    }
}
