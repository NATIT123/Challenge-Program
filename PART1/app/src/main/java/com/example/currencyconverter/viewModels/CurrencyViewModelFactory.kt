package com.example.currencyconverter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.Database.CurrencyDatabase

class CurrencyViewModelFactory(private val currencyDatabase: CurrencyDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyDatabase) as T
    }
}