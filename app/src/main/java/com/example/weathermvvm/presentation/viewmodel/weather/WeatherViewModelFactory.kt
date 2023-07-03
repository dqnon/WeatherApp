package com.example.weathermvvm.presentation.viewmodel.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.repository.WeatherRepositoryImpl
import com.example.weathermvvm.domain.UseCase.*

class WeatherViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()

    private val changeBackgroundUseCase = ChangeBackgroundUseCase()

    private val getForecastUseCase = GetForecastUseCase(weatherRepository)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            changeBackgroundUseCase = changeBackgroundUseCase,
            getForecastUseCase = getForecastUseCase,
        ) as T
    }
}