package com.example.weathermvvm.presentation.viewmodel.main

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase2
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val getLocationUseCase: GetLocationUseCase,
     private val saveCityListUseCase: SaveCityListUseCase,
     private val getLocationUseCase2: GetLocationUseCase2): ViewModel() {

    val resultCoord = MutableLiveData<List<Double>>()//delete
    val allItemsCity = MutableLiveData<MutableList<WeatherCityItem>>()

    val locationLiveData = MutableLiveData<Location?>()



    fun addCityRoom(city: WeatherCityItem) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().saveCity(city)
            //getAllItems()
        }

    fun delCityRoom(city: WeatherCityItem) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().deleteCity(city)
            //getAllItems()
        }

    fun getAllItems() {
        val result = saveCityListUseCase.executeRoom().allNotes
        allItemsCity.postValue(result)
    }

    fun getLatLon(Latlon: List<Double>){
        resultCoord.value = Latlon
        Log.d("listLog", "${resultCoord.value} 4.0")
    }

    fun getLocationData() {
        var location = getLocationUseCase.getActualLocation()
        location.addOnCompleteListener {
            try{
                getLatLon(listOf( it.result.latitude, it.result.longitude))
                Log.d("listLog", "${it.result.latitude} 3.0")
            } catch(t: Throwable){
                Log.e("CoordLog", "$t")
            }
        }
    }

    fun getLocation() {
        getLocationUseCase2.getLocation { location ->
            locationLiveData.postValue(location)
        }
    }

}