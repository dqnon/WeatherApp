package com.example.weathermvvm.domain.repository

import com.example.weathermvvm.db.CityList.WeatherCityItem

interface RoomRepository {

    val allNotes: MutableList<WeatherCityItem>

    suspend fun saveCity(item: WeatherCityItem)

    suspend fun deleteCity(item: WeatherCityItem)


}