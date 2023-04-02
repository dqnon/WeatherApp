package com.example.weathermvvm.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermvvm.Utils.Permission
import com.example.weathermvvm.domain.UseCase.*
import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.model.forecast.Forecastday
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val changeBackgroundUseCase: ChangeBackgroundUseCase,
    //private val permission: Permission,
    private val getForecastUseCase: GetForecastUseCase,
): ViewModel() {

    var resultForecast = MutableLiveData<Forecast>()
    var conditionBackGround = MutableLiveData<Int>()
    val resultCoord = MutableLiveData<String>()

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
        getLocationData()
    }

    suspend fun getForecastData(city: String) {
        val forecastWeather: Forecast = getForecastUseCase.executeForecast(city)
        resultForecast.postValue(forecastWeather)
    }

    fun getLatLon(coord: String){
        resultCoord.postValue(coord)
    }


    fun getLocationData() {
        var location = getLocationUseCase.getActualLocation()
        location.addOnCompleteListener {
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (permission.checkLocationPermission()) {
                        //getForecastData("${it.result.latitude}, ${it.result.longitude}")
                        getLatLon("${it.result.latitude}, ${it.result.longitude}")
//                    }
//                    else{
//                        // если запрещен доступ к gps
//                        permission.registerPermissionListener()
//                    }
//                }
        }
    }

    fun changeBackground(condition: String){
        val change = changeBackgroundUseCase.changeBackground(condition)
        conditionBackGround.value = change
    }

}


