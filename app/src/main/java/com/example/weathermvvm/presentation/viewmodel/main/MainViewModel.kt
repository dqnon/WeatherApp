package com.example.weathermvvm.presentation.viewmodel.main

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.*
import com.example.weathermvvm.Utils.Permission
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.google.android.gms.tasks.RuntimeExecutionException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val getLocationUseCase: GetLocationUseCase,
                    private val getCitySearchUseCase: GetCitySearchUseCase,
                    private val saveCityListUseCase: SaveCityListUseCase,
                    private val permission: Permission

): ViewModel() {

    val resultCoord = MutableLiveData<List<Double>>()
    val allItemsCity = MutableLiveData<MutableList<WeatherCityItem>>()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        CoroutineScope(Dispatchers.IO).launch {
            //weatherViewModel.getFragmentByCity(cityArg)
            Log.d("MyLog", "ЗДЕСЬ ДОЛЖЕН БЫТЬ РУМ")
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO  + coroutineExceptionHandler) {
            permission.registerPermissionListener()
            while (!permission.checkLocationPermission()){
                permission.registerPermissionListener()
            }
            if(permission.checkLocationPermission()){
                getAllItems()
                getLocationData()
            }
//            else{
//                permission.registerPermissionListener()
//            }

        }
    }


    fun addCityRoom(city: WeatherCityItem, onSuccess:() -> Unit) =
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                saveCityListUseCase.executeRoom().saveCity(city){
                    onSuccess()
                }
                getAllItems()
            }


    fun delCityRoom(city: WeatherCityItem, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            saveCityListUseCase.executeRoom().deleteCity(city){
                onSuccess()
            }
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
            } catch(e: RuntimeExecutionException){
                Log.d("CoordLog", "Ошибка при получении геолокации")
            }

        }
    }


}