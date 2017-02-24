package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaa_solutions.eazyback.R;

public final class FloatWindowSettings extends GenericActivity {

    public static final int RIGHT_MARGIN = -250;
    public static final int BOTTOM_MARGIN = -250;
    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_float_settings);
        setContentView(R.layout.activity_floated_buttons);

        RelativeLayout.LayoutParams layoutParams;
        View.OnTouchListener onTouch = new OnTouch();

        RelativeLayout acceptLayout = (RelativeLayout) findViewById(R.id.layout_accept);
        layoutParams = (RelativeLayout.LayoutParams) acceptLayout.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getAcceptButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getAcceptButtonMarginTop();
        layoutParams.rightMargin = RIGHT_MARGIN;
        layoutParams.bottomMargin = BOTTOM_MARGIN;
        acceptLayout.setLayoutParams(layoutParams);
        acceptLayout.setOnTouchListener(onTouch);

        RelativeLayout rejectLayout = (RelativeLayout) findViewById(R.id.layout_reject);
        layoutParams = (RelativeLayout.LayoutParams) rejectLayout.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getRejectButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getRejectButtonMarginTop();
        layoutParams.rightMargin = RIGHT_MARGIN;
        layoutParams.bottomMargin = BOTTOM_MARGIN;
        rejectLayout.setLayoutParams(layoutParams);
        rejectLayout.setOnTouchListener(onTouch);

        RelativeLayout delayLayout = (RelativeLayout) findViewById(R.id.layout_delay);
        layoutParams = (RelativeLayout.LayoutParams) delayLayout.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getDelayButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getDelayButtonMarginTop();
        layoutParams.rightMargin = RIGHT_MARGIN;
        layoutParams.bottomMargin = BOTTOM_MARGIN;
        delayLayout.setLayoutParams(layoutParams);
        delayLayout.setOnTouchListener(onTouch);

        RelativeLayout callbackLayout = (RelativeLayout) findViewById(R.id.layout_callback);
        layoutParams = (RelativeLayout.LayoutParams) callbackLayout.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getCallbackButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getCallbackButtonMarginLeft();
        layoutParams.rightMargin = RIGHT_MARGIN;
        layoutParams.bottomMargin = BOTTOM_MARGIN;
        callbackLayout.setLayoutParams(layoutParams);
        callbackLayout.setOnTouchListener(onTouch);

        initBackButton();
    }

    private class OnTouch implements View.OnTouchListener {

        private final String TAG = OnTouch.class.getSimpleName();

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Log.e(getClass().getSimpleName(), "view Id: " + view.getId());
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;

                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = RIGHT_MARGIN;
                    layoutParams.bottomMargin = BOTTOM_MARGIN;
                    view.setLayoutParams(layoutParams);

                    switch (view.getId()) {
                        case R.id.layout_accept:
                            Log.e(TAG, "move : accept_image");
                            getSharedHelper().setAcceptButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setAcceptButtonMarginTop(layoutParams.topMargin);
                            break;

                        case R.id.layout_reject:
                            Log.e(TAG, "move : reject_image");
                            getSharedHelper().setRejectButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setRejectButtonMarginTop(layoutParams.topMargin);
                            break;

                        case R.id.layout_delay:
                            Log.e(TAG, "move : delay_image");
                            getSharedHelper().setDelayButtonMarginLeft(layoutParams.leftMargin);
                            getSharedHelper().setDelayButtonMarginLeft(layoutParams.topMargin);
                            break;

                        case R.id.layout_callback:
                            Log.e(TAG, "move : callback_image");
                            getSharedHelper().setCallbackButtonMarginTop(layoutParams.leftMargin);
                            getSharedHelper().setCallbackButtonMarginLeft(layoutParams.topMargin);
                            break;
                    }
            }
            return true;
        }
    }
}
