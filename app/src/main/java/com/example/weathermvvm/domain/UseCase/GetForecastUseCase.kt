package com.example.weathermvvm.domain.UseCase

import android.util.Log
import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.model.forecast.ForecastX
import com.example.weathermvvm.domain.model.forecast.Forecastday
import com.example.weathermvvm.domain.repository.WeatherRepository

class GetForecastUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun executeForecast(city: String): Forecast {
        var result = weatherRepository.getForecastWeather(city)
        return result
        //Log.d("MyLog", "ТЕМПЕРАТУРА ${result.forecast.forecastday[0].hour[0].temp_c}")
    }
}