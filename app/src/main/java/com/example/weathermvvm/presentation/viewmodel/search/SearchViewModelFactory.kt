package com.example.weathermvvm.presentation.viewmodel.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.repository.WeatherRepositoryImpl
import com.example.weathermvvm.data.repository.RoomRepositoryImpl
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase

class SearchViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()
    private val getSearchListUseCase = GetSearchListUseCase(weatherRepository)

    private val db = WeatherDb.getDb(context).getDao()
    private val roomRepository = RoomRepositoryImpl(db)
    private val saveCityListUseCase = SaveCityListUseCase(roomRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            getSearchListUseCase = getSearchListUseCase,
            saveCityListUseCase = saveCityListUseCase,
        ) as T
    }
}