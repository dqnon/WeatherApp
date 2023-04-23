package com.example.weathermvvm.data

import androidx.room.TypeConverter
import com.example.weathermvvm.domain.model.forecast.Forecastday
import com.example.weathermvvm.domain.model.forecast.Hour
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGroupTaskMemberList(value: List<Forecastday>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Forecastday>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList(value: String): List<Forecastday> {
        val gson = Gson()
        val type = object : TypeToken<List<Forecastday>>() {}.type
        return gson.fromJson(value, type)
    }
}