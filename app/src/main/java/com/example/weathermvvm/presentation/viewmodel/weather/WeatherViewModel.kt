package com.example.weathermvvm.presentation.viewmodel.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.data.toRoomModel
import com.example.weathermvvm.domain.UseCase.*
import com.example.weathermvvm.domain.model.RoomModel
import com.example.weathermvvm.presentation.ProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val changeBackgroundUseCase: ChangeBackgroundUseCase,
    private val getForecastUseCase: GetForecastUseCase,
): ViewModel() {

    var resultForecast = MutableLiveData<RoomModel>()
    var conditionBackGround = MutableLiveData<Int>()
    var state = MutableLiveData<ProgressState>(ProgressState.Success)

    fun getForecastData(city: String) {
            state.postValue(ProgressState.Loading)
            try {
                viewModelScope.launch {
                    val forecastWeather: RoomModel = getForecastUseCase.executeForecast(city).toRoomModel()
                    resultForecast.postValue(forecastWeather)
                    state.postValue(ProgressState.Success)
                }

            } catch (t: Throwable){
                Log.e("TestWeather", "WeatherViewModel: ${t.message}: $t")
                state.postValue(ProgressState.Error)
            }



    }

    fun changeBackground(condition: Int){
        val change = changeBackgroundUseCase.changeBackground(condition)
        conditionBackGround.value = change
    }

}


