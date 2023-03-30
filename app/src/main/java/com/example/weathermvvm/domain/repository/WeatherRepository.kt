package com.example.weathermvvm.domain.repository

import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.model.searchCity.SearchCity

interface WeatherRepository {

    suspend fun getForecastWeather(city: String): Forecast

    suspend fun getSearchList(city: String): SearchCity

}