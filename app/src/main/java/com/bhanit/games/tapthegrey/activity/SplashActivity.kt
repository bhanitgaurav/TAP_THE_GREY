package com.bhanit.games.tapthegrey.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bhanit.games.tapthegrey.R
import com.bhanit.games.tapthegrey.helper.Log
import com.bhanit.games.tapthegrey.utils.constants.TapTheGrey

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        removeStatusBar()
        waitAndStartTapTheGreyActivity()
    }

    private fun removeStatusBar() {
        Log.d(TAG, "removeStatusBar: ")
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun waitAndStartTapTheGreyActivity() {
        Log.d(TAG, "waitAndStartTapTheGreyActivity: ")
        val runnable = Runnable { this.startDashboardActivity() }
        Handler(Looper.getMainLooper()).postDelayed(runnable, TapTheGrey.Time.ONE_SECOND.toLong())
    }

    private fun startDashboardActivity() {
        Log.d(TAG, "startDashboardActivity() method called")
        val intent = Intent(this, TapTheGreyActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG: String = SplashActivity::class.java.simpleName
    }
}