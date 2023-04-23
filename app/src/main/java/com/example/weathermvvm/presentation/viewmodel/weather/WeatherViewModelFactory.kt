package com.example.weathermvvm.presentation.viewmodel.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.data.WeatherRepositoryImpl
import com.example.weathermvvm.data.repository.FragmentRoomRepositoryImpl
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.UseCase.*

class WeatherViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val weatherRepository = WeatherRepositoryImpl()

    private val changeBackgroundUseCase = ChangeBackgroundUseCase()

    //private val permission = Permission(context, activityResultRegistry)

    private val getForecastUseCase = GetForecastUseCase(weatherRepository)


    //db fragment
    private val db = WeatherDb.getDb(context).getFragmentDao()
    private val fragmentRoomRepository = FragmentRoomRepositoryImpl(db)
    private val saveFragmentUseCase = SaveFragmentUseCase(fragmentRoomRepository)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            changeBackgroundUseCase = changeBackgroundUseCase,
            //permission = permission,
            getForecastUseCase = getForecastUseCase,
            getSaveFragmentUseCase = saveFragmentUseCase
        ) as T
    }
}