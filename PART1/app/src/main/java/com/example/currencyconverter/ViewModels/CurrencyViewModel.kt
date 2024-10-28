package com.example.currencyconverter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.Models.ConvertCurrencyResponse
import com.example.currencyconverter.Models.Query
import com.example.currencyconverter.Models.SaveCurrency
import com.example.currencyconverter.Models.SymbolResponse
import com.example.currencyconverter.Models.SymbolsName
import com.example.newsapplication.Models.Article
import com.example.newsapplication.Models.NewsResponse
import com.example.tvshowsapplication.Database.CurrencyDatabase
import com.example.tvshowsapplication.Retrofit.ApiCurrencyService
import com.example.tvshowsapplication.Retrofit.ApiNewsService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class CurrencyViewModel(private val currencyDatabase: CurrencyDatabase) : ViewModel() {
    private var currencyLiveData = MutableLiveData<SymbolsName>()
    private var currencySaveLiveData = currencyDatabase.currencyDao().getListCurrency()
    private var articlesLiveData = MutableLiveData<List<Article>>()
    private var convertCurrencyLiveData = MutableLiveData<Query>()


    fun convertCurrencyApi() {
        ApiCurrencyService.apiCurrencyService.convertCurrency(
            from = "GBP",
            to = "JPY",
            amount = 25.0
        ).enqueue(object : Callback<ConvertCurrencyResponse> {
            override fun onResponse(
                call: Call<ConvertCurrencyResponse>,
                response: Response<ConvertCurrencyResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        convertCurrencyLiveData.value = body.query
                    }
                }
            }

            override fun onFailure(p0: Call<ConvertCurrencyResponse>, p1: Throwable) {

            }

        })
    }

    fun getSymbolsApi() {
        ApiCurrencyService.apiCurrencyService.getSymbols()
            .enqueue(object : Callback<SymbolResponse> {
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


    fun getBreakingNewsApi(page: Int) {
        val cal = Calendar.getInstance()
        val currentDay = cal.get(Calendar.DAY_OF_MONTH)
        val currentMonth = cal.get(Calendar.DAY_OF_MONTH)
        val currentYear = cal.get(Calendar.YEAR)
        ApiNewsService.apiService.searchEverything(
            page = page,
            from = "${currentYear}-${
                currentMonth.toString().padStart(2, '0')
            }-${currentDay.toString().padStart(2, '0')}"
        )
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            articlesLiveData.value = body.articles
                        }
                    }
                }

                override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {

                }

            })
    }


    fun addCurrency(currency: SaveCurrency) {
        viewModelScope.launch {
            currencyDatabase.currencyDao().upsert(currency)
        }
    }


    fun observerBreakingNews(): MutableLiveData<List<Article>> {
        return articlesLiveData
    }

    fun observerSymbolCurrency(): MutableLiveData<SymbolsName> {
        return currencyLiveData
    }

    fun observerConvertCurrency(): MutableLiveData<Query> {
        return convertCurrencyLiveData
    }

    fun observerCurrencySaved(): LiveData<List<SaveCurrency>> {
        return currencySaveLiveData
    }


}