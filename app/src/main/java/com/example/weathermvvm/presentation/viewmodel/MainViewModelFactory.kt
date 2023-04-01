package com.example.weathermvvm.presentation.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.Utils.Permission
import com.example.weathermvvm.data.WeatherRepositoryImpl
import com.example.weathermvvm.data.repository.GeoPositionRepositoryImpl
import com.example.weathermvvm.domain.UseCase.*

class MainViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()

    private val geoPositionRepository = GeoPositionRepositoryImpl()
    private val getLocationUseCase = GetLocationUseCase(geoPositionRepository, context)

    private val changeBackgroundUseCase = ChangeBackgroundUseCase()

    //private val permission = Permission(context, activityResultRegistry)

    private val getForecastUseCase = GetForecastUseCase(weatherRepository)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getLocationUseCase = getLocationUseCase,
            changeBackgroundUseCase = changeBackgroundUseCase,
            //permission = permission,
            getForecastUseCase = getForecastUseCase,
        ) as T
    }
}