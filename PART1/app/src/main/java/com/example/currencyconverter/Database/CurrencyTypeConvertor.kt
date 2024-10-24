package com.example.tvshowsapplication.Database

import androidx.room.TypeConverter

class CurrencyTypeConvertor {

    @TypeConverter
    fun anyToString(attribute: Any?): String {
        if (attribute == null) {
            return ""
        }
        return attribute as String
    }

    @TypeConverter
    fun stringToAny(attribute: String?): Any {
        if (attribute == null) {
            return ""
        }
        return attribute
    }
}