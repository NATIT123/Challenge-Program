package com.example.currencyconverter.Models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currency")
class SaveCurrency(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String?,
    val rate: String?,
    val image: String?
)