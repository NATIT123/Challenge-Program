package com.example.tvshowsapplication.Retrofit

import com.example.currencyconverter.models.LatestRatesResponse
import com.example.currencyconverter.models.SymbolResponse
import com.example.tvshowsapplication.Utils.Constants.Companion.API_KEY_CURRENCY
import com.example.tvshowsapplication.Utils.Constants.Companion.BASE_URL_CURRENCY
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCurrencyService {
    companion object {
        private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        val apiCurrencyService: ApiCurrencyService by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().baseUrl(BASE_URL_CURRENCY).addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            ).client(client).build().create(ApiCurrencyService::class.java)
        }
    }


    @GET("latest")
    fun getLatestRatesCurrency(
        @Query("access_key") apiKey: String = API_KEY_CURRENCY
    ): Call<LatestRatesResponse>

    @GET("symbols")
    fun getSymbols(@Query("access_key") apiKey: String = API_KEY_CURRENCY): Call<SymbolResponse>


}