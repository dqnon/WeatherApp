package com.example.weathermvvm.presentation.viewmodel.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermvvm.data.toRoomModel
import com.example.weathermvvm.domain.UseCase.*
import com.example.weathermvvm.domain.model.RoomModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val changeBackgroundUseCase: ChangeBackgroundUseCase,
    private val getForecastUseCase: GetForecastUseCase,
): ViewModel() {

    var resultForecast = MutableLiveData<RoomModel>()
    var conditionBackGround = MutableLiveData<Int>()

    suspend fun getForecastData(city: String) {
        val forecastWeather: RoomModel = getForecastUseCase.executeForecast(city).toRoomModel()
        resultForecast.postValue(forecastWeather)
    }

    fun changeBackground(condition: Int){
        val change = changeBackgroundUseCase.changeBackground(condition)
        conditionBackGround.value = change
    }

}


