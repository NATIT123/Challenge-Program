package com.example.currencyconverter

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.currencyconverter.Utils.PreferenceManager
import com.example.currencyconverter.ViewModels.CurrencyViewModel
import com.example.currencyconverter.ViewModels.CurrencyViewModelFactory
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.tvshowsapplication.Database.CurrencyDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferenceManager: PreferenceManager

    val viewModel: CurrencyViewModel by lazy {
        val currencyDatabase = CurrencyDatabase.getInstance(this)
        val currencyViewModelFactory = CurrencyViewModelFactory(currencyDatabase)
        ViewModelProvider(this, currencyViewModelFactory)[CurrencyViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.i("TAG", "handleOnBackPressed: Exit")
                        exitOnBackPressed()
                    }
                })
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_frame) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.myBottomNav, navController)

    }

    private fun exitOnBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setTitle("Confirm Exit")
                setMessage("Are you sure you want to exist?")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                create()
                show()
            }

        }
    }
}