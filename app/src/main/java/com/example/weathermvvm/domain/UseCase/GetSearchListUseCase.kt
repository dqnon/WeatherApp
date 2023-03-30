package com.example.weathermvvm.domain.UseCase

import com.example.weathermvvm.domain.model.searchCity.SearchCity
import com.example.weathermvvm.domain.repository.WeatherRepository

class GetSearchListUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun executeSearchList(city: String): SearchCity {
        var result = weatherRepository.getSearchList(city)
        return result
    }
}