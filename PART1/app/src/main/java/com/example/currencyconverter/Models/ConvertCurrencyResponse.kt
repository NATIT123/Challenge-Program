package com.example.currencyconverter.Models

data class ConvertCurrencyResponse(
    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)