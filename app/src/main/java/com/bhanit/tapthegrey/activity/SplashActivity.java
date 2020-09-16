package com.bhanit.tapthegrey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bhanit.tapthegrey.R;
import com.bhanit.tapthegrey.helper.Log;
import com.bhanit.tapthegrey.utils.constants.TapTheGrey;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        waitAndStartTapTheGreyActivity();
    }

    private void waitAndStartTapTheGreyActivity() {
        Log.d(TAG, "waitAndStartTapTheGreyActivity: ");
        Runnable runnable = this::startDashboardActivity;
        new Handler().postDelayed(runnable, TapTheGrey.Time.FIVE_SECOND);
    }

    private void startDashboardActivity() {
        Log.d(TAG, "startDashboardActivity() method called");
        Intent intent = new Intent(this, TapTheGreyActivity.class);
        startActivity(intent);
        finish();
    }
}
