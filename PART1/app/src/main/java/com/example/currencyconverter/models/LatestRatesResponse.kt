package com.example.currencyconverter.models

data class LatestRatesResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)