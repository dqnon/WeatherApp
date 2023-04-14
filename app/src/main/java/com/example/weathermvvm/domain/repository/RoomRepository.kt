package com.example.weathermvvm.domain.repository

import androidx.lifecycle.LiveData
import com.example.weathermvvm.db.WeatherItem
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    val allNotes: LiveData<MutableList<WeatherItem>>

    suspend fun saveCity(item: WeatherItem, onSuccess:() -> Unit)

    suspend fun deleteCity(item: WeatherItem, onSuccess:() -> Unit)
}