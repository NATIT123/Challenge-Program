package com.example.currencyconverter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.utils.Constants.Companion.IS_STARTED
import com.example.currencyconverter.utils.PreferenceManager

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (preferenceManager.getBoolean(IS_STARTED)) {
                goToSignInActivity()
            } else {
                goToOnBoardingActivity()
            }
        }, 3000)
    }

    private fun goToSignInActivity() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToOnBoardingActivity() {
        val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


}