package com.example.currencyconverter.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.models.SaveCurrency

@Dao
interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currency: SaveCurrency): Long

    @Query("SELECT * FROM currency")
    fun getListCurrency(): LiveData<List<SaveCurrency>>


}