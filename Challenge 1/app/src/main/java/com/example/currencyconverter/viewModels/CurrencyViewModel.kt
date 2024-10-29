package com.example.currencyconverter.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.Database.CurrencyDatabase
import com.example.currencyconverter.models.Article
import com.example.currencyconverter.models.ErrorHandle
import com.example.currencyconverter.models.LatestRatesAToLResponse
import com.example.currencyconverter.models.LatestRatesMToZResponse
import com.example.currencyconverter.models.NewsResponse
import com.example.currencyconverter.models.RatesAToL
import com.example.currencyconverter.models.RatesMToZ
import com.example.currencyconverter.models.SaveCurrency
import com.example.currencyconverter.models.SymbolResponse
import com.example.currencyconverter.models.SymbolsName
import com.example.currencyconverter.retrofit.ApiCurrencyService
import com.example.currencyconverter.retrofit.ApiNewsService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class CurrencyViewModel(private val currencyDatabase: CurrencyDatabase) : ViewModel() {
    private var currencyLiveData = MutableLiveData<SymbolsName>()
    private var currencySaveLiveData = currencyDatabase.currencyDao().getListCurrency()
    private var articlesLiveData = MutableLiveData<List<Article>>()
    private var latestRatesAToLLiveData = MutableLiveData<RatesAToL>()
    private var latestRatesMToZLiveData = MutableLiveData<RatesMToZ>()
    private var errorHandleSymbol = MutableLiveData<ErrorHandle>()
    private var errorHandleRates = MutableLiveData<ErrorHandle>()
    private var errorHandleNews = MutableLiveData<ErrorHandle>()


    fun getLatestRatesAToL() {
        ApiCurrencyService.apiCurrencyService.getLatestRatesAToLCurrency()
            .enqueue(object : Callback<LatestRatesAToLResponse> {
                override fun onResponse(
                    call: Call<LatestRatesAToLResponse>,
                    response: Response<LatestRatesAToLResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.success) {
                            latestRatesAToLLiveData.value = body.rates
                        } else {
                            if (body != null) {
                                Log.d("MyApp", body.error.toString())
                            }
                            if (body != null) {
                                errorHandleSymbol.value = body.error
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<LatestRatesAToLResponse>, error: Throwable) {
                    errorHandleRates.value = ErrorHandle("Error", error.message.toString())
                }

            })
    }

    fun getLatestRatesMToZ() {
        ApiCurrencyService.apiCurrencyService.getLatestRatesMToZCurrency()
            .enqueue(object : Callback<LatestRatesMToZResponse> {
                override fun onFailure(call: Call<LatestRatesMToZResponse>, error: Throwable) {
                    errorHandleRates.value = ErrorHandle("Error", error.message.toString())
                }

                override fun onResponse(
                    call: Call<LatestRatesMToZResponse>,
                    response: Response<LatestRatesMToZResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.success) {
                            latestRatesMToZLiveData.value = body.rates

                        } else {
                            if (body != null) {
                                errorHandleSymbol.value = body.error
                            }
                        }
                    }
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
                        if (body != null && body.success) {
                            currencyLiveData.value = body.symbols
                        } else {
                            if (body != null) {
                                errorHandleSymbol.value = body.error
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SymbolResponse>, error: Throwable) {
                    errorHandleSymbol.value = ErrorHandle("Error", error.message.toString())
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
                        if (body != null && body.status == "ok") {
                            articlesLiveData.value = body.articles
                        } else {
                            if (body != null && body.status == "error") {
                                errorHandleNews.value = ErrorHandle("Error", body.message)
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<NewsResponse>, error: Throwable) {
                    errorHandleNews.value = ErrorHandle("Error", error.message.toString())
                }

            })
    }


    fun addCurrency(listCurrency: List<SaveCurrency>) {
        viewModelScope.launch {
            currencyDatabase.currencyDao().upsertAll(listCurrency)
        }
    }


    fun observerBreakingNews(): MutableLiveData<List<Article>> {
        return articlesLiveData
    }

    fun observerSymbolCurrency(): MutableLiveData<SymbolsName> {
        return currencyLiveData
    }

    fun observerLatestRatesAToL(): MutableLiveData<RatesAToL> {
        return latestRatesAToLLiveData
    }

    fun observerLatestRatesMToZ(): MutableLiveData<RatesMToZ> {
        return latestRatesMToZLiveData
    }

    fun observerCurrencySaved(): LiveData<List<SaveCurrency>> {
        return currencySaveLiveData
    }

    fun observerErrorHandleSymbol(): MutableLiveData<ErrorHandle> {
        return errorHandleSymbol
    }

    fun observerErrorHandleRates(): MutableLiveData<ErrorHandle> {
        return errorHandleRates
    }

    fun observerErrorHandleNews(): MutableLiveData<ErrorHandle> {
        return errorHandleNews
    }


}