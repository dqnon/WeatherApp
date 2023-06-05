package com.example.weathermvvm.presentation.viewmodel.main

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.weathermvvm.Utils.Permission
import com.example.weathermvvm.data.repository.GeoPositionRepositoryImpl
import com.example.weathermvvm.data.repository.RoomRepositoryImpl
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem

class MainViewModelFactory(context: Context, activityResultRegistry: ActivityResultRegistry)
    : ViewModelProvider.Factory {

    private val geoPositionRepository = GeoPositionRepositoryImpl()
    private val getLocationUseCase = GetLocationUseCase(geoPositionRepository, context)
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    private val getCitySearchUseCase = GetCitySearchUseCase(activityResultRegistry){}

    private val activityResultRegistry = activityResultRegistry

    private val db = WeatherDb.getDb(context).getDao()
    private val roomRepository = RoomRepositoryImpl(db)
    private val saveCityListUseCase = SaveCityListUseCase(roomRepository)
    private val permission = Permission(context, activityResultRegistry)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getLocationUseCase = getLocationUseCase,
            //activityResultRegistry = activityResultRegistry,
            saveCityListUseCase = saveCityListUseCase,
            getCitySearchUseCase = getCitySearchUseCase,
            //someActivityResultLauncher = activityResultLauncher
        //activityResultRegistry = activityResultRegistry
            permission = permission
        ) as T
    }
}