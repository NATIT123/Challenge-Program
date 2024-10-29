package com.example.currencyconverter


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.currencyconverter.Database.CurrencyDatabase
import com.example.currencyconverter.utils.PreferenceManager
import com.example.currencyconverter.viewModels.CurrencyViewModel
import com.example.currencyconverter.viewModels.CurrencyViewModelFactory
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.utils.NetworkManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferenceManager: PreferenceManager
    var isConnected = false

    val viewModel: CurrencyViewModel by lazy {
        val currencyDatabase = CurrencyDatabase.getInstance(this)
        val currencyViewModelFactory = CurrencyViewModelFactory(currencyDatabase)
        ViewModelProvider(this, currencyViewModelFactory)[CurrencyViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ///Check network connection
        val networkManager = NetworkManager(this)
        networkManager.observe(this) {
            if (it) {
                isConnected = true
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                isConnected = false
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            }
        }


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