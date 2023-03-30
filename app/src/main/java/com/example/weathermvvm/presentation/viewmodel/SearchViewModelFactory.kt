package com.example.weathermvvm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.WeatherRepositoryImpl
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase

class SearchViewModelFactory(): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()
    private val getSearchListUseCase = GetSearchListUseCase(weatherRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            getSearchListUseCase = getSearchListUseCase
        ) as T
    }
}