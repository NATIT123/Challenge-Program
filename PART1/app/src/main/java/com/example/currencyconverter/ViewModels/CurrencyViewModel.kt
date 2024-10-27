package com.example.currencyconverter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.Models.CountryItem
import com.example.currencyconverter.Models.CountryResponse
import com.example.currencyconverter.Models.SaveCurrency
import com.example.currencyconverter.Models.SymbolResponse
import com.example.currencyconverter.Models.SymbolsName
import com.example.currencyconverter.R
import com.example.tvshowsapplication.Database.CurrencyDatabase
import com.example.tvshowsapplication.Retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyViewModel(private val currencyDatabase: CurrencyDatabase) : ViewModel() {
    private var currencyLiveData = MutableLiveData<SymbolsName>()
    private var currencySaveLiveData = currencyDatabase.currencyDao().getListCurrency()
    private var flagLiveData = MutableLiveData<List<CountryItem>>()


    fun getSymbolsApi() {
        ApiService.apiService.getSymbols().enqueue(object : Callback<SymbolResponse> {
            override fun onResponse(
                call: Call<SymbolResponse>,
                response: Response<SymbolResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        currencyLiveData.value = body.symbols
                    }
                }
            }

            override fun onFailure(p0: Call<SymbolResponse>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFlagsApi(name: String) {
        ApiService.apiService.getFlag(name).enqueue(object : Callback<CountryResponse> {
            override fun onResponse(
                call: Call<CountryResponse>,
                response: Response<CountryResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        flagLiveData.value = body
                    }
                }
            }

            override fun onFailure(p0: Call<CountryResponse>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun addCurrency(currency: SaveCurrency) {
        viewModelScope.launch {
            currencyDatabase.currencyDao().upsert(currency)
        }
    }

    fun observerSymbolCurrency(): MutableLiveData<SymbolsName> {
        return currencyLiveData
    }

    fun observerCurrencySaved(): LiveData<List<SaveCurrency>> {
        return currencySaveLiveData
    }

    fun observerFlag(): MutableLiveData<List<CountryItem>> {
        return flagLiveData
    }
}