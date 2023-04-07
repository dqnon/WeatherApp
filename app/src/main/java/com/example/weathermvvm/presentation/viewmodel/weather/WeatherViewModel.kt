package com.example.weathermvvm.presentation.viewmodel.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermvvm.domain.UseCase.*
import com.example.weathermvvm.domain.model.forecast.Forecast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val changeBackgroundUseCase: ChangeBackgroundUseCase,
    //private val permission: Permission,
    private val getForecastUseCase: GetForecastUseCase,
): ViewModel() {

    var resultForecast = MutableLiveData<Forecast>()
    var conditionBackGround = MutableLiveData<Int>()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MyLog", "ЗДЕСЬ ДОЛЖЕН БЫТЬ РУМ")
        }
    }

    init {
//        permission.registerPermissionListener()
//        permission.checkLocationPermission()
//        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
//            getLocationData()
//        }
    }



    suspend fun getForecastData(city: String) {
        val forecastWeather: Forecast = getForecastUseCase.executeForecast(city)
        resultForecast.postValue(forecastWeather)
    }




    fun changeBackground(condition: Int){
        val change = changeBackgroundUseCase.changeBackground(condition)
        conditionBackGround.value = change
    }

}


