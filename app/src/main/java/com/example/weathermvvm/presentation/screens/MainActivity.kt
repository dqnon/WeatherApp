package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    //val cityList: MutableList<String> = mutableListOf("")

    private val adapter = VpAdapter(this)

    lateinit var citySearch : GetCitySearchUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this, activityResultRegistry))
            .get(MainViewModel::class.java)

        mainViewModel.resultCoord.observe(this, Observer {
            adapter.list[0] = WeatherItem(null, it)
            //cityList[0] = it
            binding.viewPager.adapter = adapter
            binding.indicator.setViewPager(binding.viewPager)
        })

        //db
        val db = WeatherDb.getDb(this)

        mainViewModel.getAllItems().observe(this, Observer { list ->
            list.forEach {
                adapter.list.add(it)
            }
                //adapter.setCityList(list)
        })


        fun addCity(city: SearchCityItem){
            adapter.addCity(city)
            //обновление индикатора
            binding.indicator.setViewPager(binding.viewPager)
            //открытие страницы с выбранным городом
            binding.viewPager.currentItem = adapter.list.size
            Log.d("CityLog", "${adapter.list.size}")
        }

        //добавление города
        citySearch = GetCitySearchUseCase(activityResultRegistry){
            addCity(it)
            //bd
            val itemWeather = WeatherItem(null, it.name)
            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().insertWeather(itemWeather)
            }
            //bd2
    }
        //удаление города
        binding.btDelete.setOnClickListener {
            val position = binding.viewPager.currentItem

            //bd
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("dblog", "ДО ${position}")
                db.getDao().deleteCity(adapter.list[position])
                runOnUiThread {
                    adapter.deleteCity(position)
                    binding.indicator.setViewPager(binding.viewPager)
                }
            }
            //bd2
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







