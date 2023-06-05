package com.example.weathermvvm.db.CityList

import androidx.room.*
import androidx.room.Dao



@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(item: WeatherCityItem)

    @Delete
    suspend fun deleteCity(item: WeatherCityItem)

    @Query("SELECT * FROM weather")
    fun getAllItem(): MutableList<WeatherCityItem>


}