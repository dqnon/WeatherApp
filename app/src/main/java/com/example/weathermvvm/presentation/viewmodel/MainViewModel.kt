package com.example.weathermvvm.presentation.viewmodel

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem

class MainViewModel(private val getLocationUseCase: GetLocationUseCase,
                    //private val activityResultRegistry: ActivityResultRegistry

): ViewModel() {

    init {
        getLocationData()
    }

    val resultCoord = MutableLiveData<String>()
//    val resultSearchCity = MutableLiveData<SearchCityItem>()




//    fun setCitySearch(): GetCitySearchUseCase{
//        val result = GetCitySearchUseCase(activityResultRegistry){
//            resultSearchCity.value = it
//        }
//        return result
//    }



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