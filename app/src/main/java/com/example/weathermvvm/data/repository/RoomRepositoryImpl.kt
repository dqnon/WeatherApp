package com.example.weathermvvm.data.repository

import com.example.weathermvvm.db.CityList.Dao
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.repository.RoomRepository

class RoomRepositoryImpl(private val weatherDao: Dao): RoomRepository {
    override val allNotes: MutableList<WeatherCityItem>
        get() = weatherDao.getAllItem()


    override suspend fun saveCity(item: WeatherCityItem, onSuccess: () -> Unit) {
        weatherDao.insertWeather(item)
        onSuccess()
    }

    override suspend fun deleteCity(item: WeatherCityItem, onSuccess: () -> Unit) {
        weatherDao.deleteCity(item)
        onSuccess()
    }

    override suspend fun updateGeoLocation(item: String, geoLocation: Int, onSuccess: () -> Unit) {
        weatherDao.updateGeolocation(item, geoLocation)
        onSuccess()
    }
}