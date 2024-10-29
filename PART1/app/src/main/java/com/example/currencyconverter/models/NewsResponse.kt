package com.example.currencyconverter.models


data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
    val code: String,
    val message: String
)