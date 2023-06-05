package com.example.weathermvvm.presentation.viewmodel.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchListUseCase: GetSearchListUseCase,
    private val saveCityListUseCase: SaveCityListUseCase,
): ViewModel() {

    val allItemsCity = MutableLiveData<MutableList<WeatherCityItem>>()
    var resultSearch = MutableLiveData<SearchCity>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllItems()
        }
    }



    fun getAllItems() {
        val result = saveCityListUseCase.executeRoom().allNotes
        allItemsCity.postValue(result)
    }

    suspend fun getCityList(city: String){
        var result = getSearchListUseCase.executeSearchList(city)
        resultSearch.postValue(result)
    }
}