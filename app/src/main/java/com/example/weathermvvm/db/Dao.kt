package com.example.weathermvvm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    suspend fun insertWeather(item: WeatherItem)

    @Delete
    suspend fun deleteCity(item: WeatherItem)

    @Query("SELECT * FROM weather")
    fun getAllItem(): LiveData<MutableList<WeatherItem>>

}