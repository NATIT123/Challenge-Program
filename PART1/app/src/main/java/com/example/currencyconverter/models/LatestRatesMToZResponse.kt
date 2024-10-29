package com.example.currencyconverter.models

data class LatestRatesMToZResponse(
    val base: String,
    val date: String,
    val rates: RatesMToZ,
    val success: Boolean,
    val timestamp: Int,
    val error: ErrorHandle
)