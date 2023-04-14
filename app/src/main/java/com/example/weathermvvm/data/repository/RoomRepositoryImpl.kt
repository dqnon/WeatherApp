package com.example.weathermvvm.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.weathermvvm.db.Dao
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.db.WeatherItem
import com.example.weathermvvm.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl(private val weatherDao: Dao): RoomRepository {
    override val allNotes: LiveData<MutableList<WeatherItem>>
        get() = weatherDao.getAllItem()


    override suspend fun saveCity(item: WeatherItem, onSuccess: () -> Unit) {
        weatherDao.insertWeather(item)
        onSuccess()
    }

    override suspend fun deleteCity(item: WeatherItem, onSuccess: () -> Unit) {
        weatherDao.deleteCity(item)
        onSuccess()
    }
}