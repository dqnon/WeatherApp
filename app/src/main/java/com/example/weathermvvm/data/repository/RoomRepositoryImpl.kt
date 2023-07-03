package com.example.weathermvvm.data.repository

import com.example.weathermvvm.db.CityList.Dao
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.repository.RoomRepository

class RoomRepositoryImpl(private val weatherDao: Dao): RoomRepository {
    override val allNotes: MutableList<WeatherCityItem>
        get() = weatherDao.getAllItem()


    override suspend fun saveCity(item: WeatherCityItem) {
        weatherDao.insertWeather(item)
    }

    override suspend fun deleteCity(item: WeatherCityItem) {
        weatherDao.deleteCity(item)
    }


}