package com.example.currencyconverter.retrofit


import com.example.currencyconverter.models.NewsResponse
import com.example.currencyconverter.utils.Constants.Companion.API_KEY_NEWS
import com.example.currencyconverter.utils.Constants.Companion.BASE_URL_NEWS
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNewsService {
    companion object {
        private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        val apiService: ApiNewsService by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().baseUrl(BASE_URL_NEWS).addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            ).client(client).build().create(ApiNewsService::class.java)
        }
    }


    @GET("everything")
    fun searchEverything(
        @Query("q") key: String = "currency",
        @Query("from") from: String = "2024-06-27",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS,
        @Query("page") page: Int = 1
    ): Call<NewsResponse>


}