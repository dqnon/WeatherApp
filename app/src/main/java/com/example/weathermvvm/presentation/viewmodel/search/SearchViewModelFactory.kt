package com.example.weathermvvm.presentation.viewmodel.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.repository.WeatherRepositoryImpl
import com.example.weathermvvm.data.repository.RoomRepositoryImpl
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase

class SearchViewModelFactory(): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()
    private val getSearchListUseCase = GetSearchListUseCase(weatherRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            getSearchListUseCase = getSearchListUseCase,
        ) as T
    }
}