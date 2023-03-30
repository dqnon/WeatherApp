package com.example.weathermvvm.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchListUseCase: GetSearchListUseCase
): ViewModel() {

    init {
        //CoroutineScope(Dispatchers.IO).launch {
            //getCityList("Lon")
        //}
    }

    var resultSearch = MutableLiveData<SearchCity>()

    suspend fun getCityList(city: String){
        var result = getSearchListUseCase.executeSearchList(city)
        resultSearch.postValue(result)
    }
}