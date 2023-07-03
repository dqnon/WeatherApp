package com.example.weathermvvm.presentation.viewmodel.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val getLocationUseCase: GetLocationUseCase,
     private val saveCityListUseCase: SaveCityListUseCase, ): ViewModel() {

    val resultCoord = MutableLiveData<List<Double>>()
    val allItemsCity = MutableLiveData<MutableList<WeatherCityItem>>()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MyLog", "ЗДЕСЬ ДОЛЖЕН БЫТЬ РУМ")
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllItems()
        }

    }

    fun addCityRoom(city: WeatherCityItem) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().saveCity(city)
            getAllItems()
        }


    fun delCityRoom(city: WeatherCityItem) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().deleteCity(city)
        }

    fun getAllItems() {
        val result = saveCityListUseCase.executeRoom().allNotes
        allItemsCity.postValue(result)
    }

    fun getLatLon(Latlon: List<Double>){
        resultCoord.postValue(Latlon)
    }

    fun getLocationData() {
        var location = getLocationUseCase.getActualLocation()
        location.addOnCompleteListener {
            try{
                getLatLon(listOf( it.result.latitude, it.result.longitude))
            } catch(t: Throwable){
                Log.d("CoordLog", "$t")
            }

        }
    }


}