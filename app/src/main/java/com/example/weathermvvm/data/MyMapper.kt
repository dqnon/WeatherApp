package com.example.weathermvvm.data

import com.example.weathermvvm.domain.model.RoomModel
import com.example.weathermvvm.domain.model.forecast.Forecast

fun Forecast.toRoomModel() = RoomModel(
    temp = this.current.temp_c,
    locationName = this.location.name,
    conditionText = this.current.condition.text,
    conditionCode = this.current.condition.code,
    lastUpdated = this.current.last_updated,
    forecastday = this.forecast.forecastday

)