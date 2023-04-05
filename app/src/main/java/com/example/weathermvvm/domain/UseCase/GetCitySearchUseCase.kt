package com.example.weathermvvm.domain.UseCase

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.SearchActivity

class GetCitySearchUseCase(private val activityResultRegistry: ActivityResultRegistry,
                           private val callback: (city: SearchCityItem) -> Unit) {

    private val getCity: ActivityResultLauncher<Intent> =
        activityResultRegistry.register("city1", ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK) {
                callback(it.data?.getSerializableExtra("city") as SearchCityItem)
            }
    }

    fun startActivity(context: Context){
        getCity?.launch(Intent(context, SearchActivity::class.java))
    }
}