package com.kaa_solutions.eazyback.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaa_solutions.eazyback.R;

public final class FloatWindowSettings extends GenericActivity {
    RelativeLayout relativeLayoutAccept;
    RelativeLayout.LayoutParams layoutParams;
    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_activity_float_settings);
        setContentView(R.layout.activity_floated_buttons);

        relativeLayoutAccept = (RelativeLayout) findViewById(R.id.layout_accept);
        layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAccept.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getAcceptButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getAcceptButtonMarginTop();
        layoutParams.rightMargin = -250;
        layoutParams.bottomMargin = -250;
        relativeLayoutAccept.setLayoutParams(layoutParams);
        relativeLayoutAccept.setOnTouchListener(new TouchListener());

        relativeLayoutAccept = (RelativeLayout) findViewById(R.id.layout_delay);
        layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAccept.getLayoutParams();
        layoutParams.leftMargin = getSharedHelper().getDelayButtonMarginLeft();
        layoutParams.topMargin = getSharedHelper().getDelayButtonMarginTop();
        layoutParams.rightMargin = -250;
        layoutParams.bottomMargin = -250;
        relativeLayoutAccept.setLayoutParams(layoutParams);
        relativeLayoutAccept.setOnTouchListener(new TouchListener());


        initBackButton();

    }


    private final class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Log.e(getClass().getSimpleName(), "view Id: " + view.getId());
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();

            int leftMargin = 0;
            int topMargin = 0;

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;


                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    leftMargin = layoutParams.leftMargin = X - xDelta;
                    topMargin = layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    switch (view.getId()) {
                        case 2131361813:
                            getSharedHelper().setAcceptButtonMarginLeft(leftMargin);
                            getSharedHelper().setAcceptButtonMarginTop(topMargin);
                            break;

                        case 2131361817:
                            getSharedHelper().setDelayButtonMarginLeft(leftMargin);
                            getSharedHelper().setDelayButtonMarginTop(topMargin);
                            break;
                    }

                    break;

            }

            return true;
        }
    }
}
