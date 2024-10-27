package com.example.tvshowsapplication.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.Models.SaveCurrency

@Dao
interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currency: SaveCurrency): Long

    @Query("SELECT * FROM currency")
    fun getListCurrency(): LiveData<List<SaveCurrency>>


}