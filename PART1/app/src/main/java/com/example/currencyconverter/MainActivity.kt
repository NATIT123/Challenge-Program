package com.example.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.currencyconverter.Database.CurrencyDatabase
import com.example.currencyconverter.utils.PreferenceManager
import com.example.currencyconverter.viewModels.CurrencyViewModel
import com.example.currencyconverter.viewModels.CurrencyViewModelFactory
import com.example.currencyconverter.databinding.ActivityMainBinding

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


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_frame) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.myBottomNav, navController)

    }


}