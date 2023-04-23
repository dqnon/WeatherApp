package com.example.weathermvvm.presentation.viewmodel.main

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.*
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val getLocationUseCase: GetLocationUseCase,
                    private val getCitySearchUseCase: GetCitySearchUseCase,
                    private val saveCityListUseCase: SaveCityListUseCase,

): ViewModel() {

    val resultCoord = MutableLiveData<String>()
    val allItemsCity = MutableLiveData<MutableList<WeatherCityItem>>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllItems()
            getLocationData()
        }
    }


    fun addCityRoom(city: WeatherCityItem, onSuccess:() -> Unit) =
            viewModelScope.launch(Dispatchers.IO) {
                saveCityListUseCase.executeRoom().saveCity(city){
                    onSuccess()
                }
                getAllItems()
            }


    fun delCityRoom(city: WeatherCityItem, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().deleteCity(city){
                onSuccess()
            }
        }


    fun getAllItems() {
        val result = saveCityListUseCase.executeRoom().allNotes
        allItemsCity.postValue(result)
    }

    fun getLatLon(coord: String){
        resultCoord.postValue(coord)
    }

    fun getLocationData() {
        var location = getLocationUseCase.getActualLocation()
        location.addOnCompleteListener {
            getLatLon("${it.result.latitude}, ${it.result.longitude}")
        }
    }


}