package com.example.weathermvvm.presentation.viewmodel.main

import androidx.lifecycle.*
import com.example.weathermvvm.db.WeatherItem
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainViewModel(private val getLocationUseCase: GetLocationUseCase,
                    //private val activityResultRegistry: ActivityResultRegistry,
                    private val saveCityListUseCase: SaveCityListUseCase,


): ViewModel() {

    val resultCoord = MutableLiveData<String>()
    val allItemsCity = MutableLiveData<MutableList<WeatherItem>>()

    init {
        //initDataBase()
        viewModelScope.launch(Dispatchers.IO) {
            getAllItems()
        }

        getLocationData()

    }

    fun addCityRoom(city: WeatherItem, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().saveCity(city){
                onSuccess()
            }
        }

    fun delCityRoom(city: WeatherItem, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            saveCityListUseCase.executeRoom().deleteCity(city){
                onSuccess()
            }
        }

    fun initDataBase(){
        //пока в MA
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