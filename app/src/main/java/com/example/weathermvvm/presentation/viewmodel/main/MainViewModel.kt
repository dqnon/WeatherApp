package com.example.weathermvvm.presentation.viewmodel.main

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weathermvvm.db.Dao
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.db.WeatherItem
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.UseCase.GetLocationUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val getLocationUseCase: GetLocationUseCase,
                    //private val activityResultRegistry: ActivityResultRegistry,
                    private val saveCityListUseCase: SaveCityListUseCase,
                    private val db: Dao

): ViewModel() {

    init {
        //initDataBase()
        getLocationData()
    }

    val resultCoord = MutableLiveData<String>()

    //val dbRoom = MutableLiveData<LiveData<List<WeatherItem>>>()


    fun initDataBase(){
        //пока в MA
    }

    fun getAllItems(): LiveData<MutableList<WeatherItem>> {
        return saveCityListUseCase.executeRoom().allNotes
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