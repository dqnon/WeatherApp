package com.example.weathermvvm.domain.model.forecast

data class Forecast(
    val current: Current,
    val forecast: ForecastX,
    val location: Location
)