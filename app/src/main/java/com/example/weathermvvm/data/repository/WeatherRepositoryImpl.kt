package com.example.weathermvvm.data

import com.example.weathermvvm.data.api.WeatherApi
import com.example.weathermvvm.domain.model.RoomModel
import com.example.weathermvvm.domain.repository.WeatherRepository
import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.model.searchCity.SearchCity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


class WeatherRepositoryImpl: WeatherRepository {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val weatherApi = retrofit.create(WeatherApi::class.java)

    val systemLanguage = Locale.getDefault().language

    override suspend fun getForecastWeather(city: String): Forecast {
        val forecastWeather = weatherApi.getForecastWeather(city, systemLanguage)

        return forecastWeather

    }

    override suspend fun getSearchList(city: String): SearchCity {
        val searchCity = weatherApi.getSearchList(city, systemLanguage)
        return searchCity
    }
}