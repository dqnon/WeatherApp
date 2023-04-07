package com.example.weathermvvm.presentation.viewmodel.main

import android.content.Context
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.repository.GeoPositionRepositoryImpl
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase

class MainViewModelFactory(context: Context, activityResultRegistry: ActivityResultRegistry): ViewModelProvider.Factory {

    private val geoPositionRepository = GeoPositionRepositoryImpl()
    private val getLocationUseCase = GetLocationUseCase(geoPositionRepository, context)
//    private val activityResultRegistry = activityResultRegistry
//    private val getCitySearchUseCase = GetCitySearchUseCase(activityResultRegistry, )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getLocationUseCase = getLocationUseCase,
            //activityResultRegistry = activityResultRegistry
        ) as T
    }
}