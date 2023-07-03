package com.example.weathermvvm.presentation.viewmodel.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetSearchListUseCase
import com.example.weathermvvm.domain.UseCase.SaveCityListUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCity
import com.example.weathermvvm.presentation.ProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchListUseCase: GetSearchListUseCase,
): ViewModel() {


    var resultSearch = MutableLiveData<SearchCity>()
    var state = MutableLiveData<ProgressState>(ProgressState.Success)


    fun getCityList(city: String){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(ProgressState.Loading)
            try {
                var result = getSearchListUseCase.executeSearchList(city)
                resultSearch.postValue(result)
                state.postValue(ProgressState.Success)
            } catch (t: Throwable){
                Log.e("Test", "${t.message}: $t")
                state.postValue(ProgressState.Error)
            }

        }
    }
}