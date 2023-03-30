package com.example.weathermvvm.data.api

import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.model.searchCity.SearchCity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast.json?key=770c2027b7ee4720873103832220907&days=3&aqi=no&alerts=no")
    suspend fun getForecastWeather(@Query("q") city: String): Forecast

    @GET("v1/search.json?key=770c2027b7ee4720873103832220907")
    suspend fun getSearchList(@Query("q") city: String): SearchCity
}