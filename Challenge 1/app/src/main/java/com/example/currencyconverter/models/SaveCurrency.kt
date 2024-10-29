package com.example.currencyconverter.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currency")
class SaveCurrency(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String?,
    val code: String?,
    val rate: Double?,
)