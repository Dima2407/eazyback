package com.kaa_solutions.eazyback.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainSettingsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
