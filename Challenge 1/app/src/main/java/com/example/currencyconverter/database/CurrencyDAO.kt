package com.example.currencyconverter.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.currencyconverter.models.SaveCurrency

@Dao
interface CurrencyDAO {

    @Upsert
    suspend fun upsertAll(currency: List<SaveCurrency>)

    @Query("SELECT * FROM currency")
    fun getListCurrency(): LiveData<List<SaveCurrency>>


}