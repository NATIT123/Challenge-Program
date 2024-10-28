package com.example.currencyconverter.Models

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)