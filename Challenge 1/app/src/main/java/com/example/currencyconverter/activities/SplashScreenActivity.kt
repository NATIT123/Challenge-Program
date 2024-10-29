package com.example.currencyconverter.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.utils.Constants.Companion.IS_STARTED
import com.example.currencyconverter.utils.PreferenceManager

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        ///Using handler to check first time open app. After 3s, check state first time open app
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            //If not first time go to home screen
            if (preferenceManager.getBoolean(IS_STARTED)) {
                goToHomeScreen()
            } else {
                //If first time open app, go to on boarding screen
                goToOnBoardingActivity()
            }
        }, 3000)
    }

    private fun goToHomeScreen() {
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