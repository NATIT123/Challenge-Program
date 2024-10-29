package com.example.currencyconverter.models

data class LatestRatesAToLResponse(
    val base: String,
    val date: String,
    val rates: RatesAToL,
    val success: Boolean,
    val timestamp: Int,
    val error: ErrorHandle
)