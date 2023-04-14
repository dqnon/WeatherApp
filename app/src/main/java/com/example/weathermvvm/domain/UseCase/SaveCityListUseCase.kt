package com.example.weathermvvm.domain.UseCase

import android.content.Context
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.repository.RoomRepository

class SaveCityListUseCase(private val roomRepository: RoomRepository, private val context: Context) {



    fun executeRoom(): RoomRepository {
        return roomRepository
    }
}