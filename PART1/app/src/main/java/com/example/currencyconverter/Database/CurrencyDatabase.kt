package com.example.tvshowsapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.Models.FavoriteCurrency

@Database(entities = [FavoriteCurrency::class], version = 1)
@TypeConverters(CurrencyTypeConvertor::class)
abstract class CurrencyDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: CurrencyDatabase? = null
        private const val DATABASE_NAME = "currency.db"

        @Synchronized
        fun getInstance(context: Context): CurrencyDatabase {
            if (INSTANCE == null) {
                synchronized(CurrencyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CurrencyDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun currencyDao(): CurrencyDAO
}