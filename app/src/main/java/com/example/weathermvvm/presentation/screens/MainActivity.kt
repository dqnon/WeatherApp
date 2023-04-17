package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.db.WeatherItem
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    private val adapter = VpAdapter(this)
    lateinit var citySearch : GetCitySearchUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this, activityResultRegistry))
            .get(MainViewModel::class.java)

        mainViewModel.resultCoord.observe(this, Observer { location ->
            adapter.list[0] = WeatherItem(null, location)
            binding.viewPager.adapter = adapter
            binding.indicator.setViewPager(binding.viewPager)
        })

        mainViewModel.allItemsCity.observe(this, Observer { list ->
                list.forEach {
                    adapter.list.add(it)
                }
        })

        fun addCity(city: SearchCityItem){
            adapter.addCity(city)
            //обновление индикатора
            binding.indicator.setViewPager(binding.viewPager)
            //открытие страницы с выбранным городом
            binding.viewPager.currentItem = adapter.list.size
            Log.d("CityLog", "${adapter.list.size}")
            Log.d("buglog", "${adapter.list}")
        }

        //добавление города
        //перенести в вьюмонль
        citySearch = GetCitySearchUseCase(activityResultRegistry) {

            //добавление в бд
            val itemWeather = WeatherItem(null, it.name)
            mainViewModel.addCityRoom(itemWeather) {}
            Log.d("adapLog1", "$itemWeather")

            //добавление в массив
            addCity(it)
            Log.d("adapLog1", "$it")
    }

        //удаление города
        binding.btDelete.setOnClickListener {
            val position = binding.viewPager.currentItem
            //удаление из бд
            mainViewModel.delCityRoom(adapter.list[position]){}
            //удаление из массива
            adapter.deleteCity(position)
            binding.indicator.setViewPager(binding.viewPager)
        }

        binding.btSearchActivity.setOnClickListener {
            citySearch.startActivity(this)
        }

        //скрытие кнопки удаления на странице с геолокацией
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.btDelete.setImageResource(R.drawable.location_image)
                    binding.btDelete.isClickable = false
                } else {
                    binding.btDelete.setImageResource(R.drawable.delete_city)
                    binding.btDelete.isClickable = true
                }
            }
        })
    }



}







