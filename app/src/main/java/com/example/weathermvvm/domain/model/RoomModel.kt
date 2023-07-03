package com.example.weathermvvm.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathermvvm.domain.model.forecast.Forecastday


data class RoomModel (
    val id: Int? = null,
    val temp: Double,
    val locationName: String,
    val conditionText: String,
    val conditionCode: Int,
    val lastUpdated: String,
    val forecastday: List<Forecastday>,
    val lat: Double,
    val lon: Double,

        )