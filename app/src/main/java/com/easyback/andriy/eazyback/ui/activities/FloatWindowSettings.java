package com.easyback.andriy.eazyback.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.easyback.andriy.eazyback.R;

public final class FloatWindowSettings extends GenericActivity {
    private int xDelta;
    private int yDelta;
    private ViewGroup mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_settings);

        mRootLayout = (ViewGroup) findViewById(R.id.float_window_root);
        findViewById(R.id.call_panel_window_root).setOnTouchListener(new TouchListener());
    }


    private final class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;

                case MotionEvent.ACTION_UP:
                    Log.e("VU", "x = " + (X - xDelta));
                    Log.e("VU", "y = "+ (Y -yDelta));

                    getSharedHelper().setFloatWindowX((int)view.getTranslationX());
                    getSharedHelper().setFloatWindowY((int)view.getTranslationY());
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);

                    break;
            }
            mRootLayout.invalidate();
            return true;
        }
    }
}
