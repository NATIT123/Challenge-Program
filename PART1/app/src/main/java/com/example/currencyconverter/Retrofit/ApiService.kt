package com.example.tvshowsapplication.Retrofit

import com.example.currencyconverter.Models.CountryResponse
import com.example.currencyconverter.Models.SymbolResponse
import com.example.newsapplication.Models.NewsResponse
import com.example.tvshowsapplication.Utils.Constants.Companion.API_KEY_CURRENCY
import com.example.tvshowsapplication.Utils.Constants.Companion.API_KEY_NEWS
import com.example.tvshowsapplication.Utils.Constants.Companion.BASE_URL_CURRENCY
import com.example.tvshowsapplication.Utils.Constants.Companion.BASE_URL_FLAGS
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        val apiService: ApiService by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().baseUrl(BASE_URL_FLAGS).addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            ).client(client).build().create(ApiService::class.java)
        }
    }


    @GET("top-headlines")
    fun getBreakingNews(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = API_KEY_NEWS,
        @Query("page") page: Int = 1
    ): Call<NewsResponse>


    @GET("everything")
    fun searchEverything(
        @Query("q") key: String,
        @Query("from") from: String = "2024-06-27",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Call<NewsResponse>

    @GET("symbols")
    fun getSymbols(@Query("access_key") apiKey: String = API_KEY_CURRENCY): Call<SymbolResponse>


    @GET("name/{name}")
    fun getFlag(@Path("name") name: String): Call<CountryResponse>

}