package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModelFactory
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    val cityList: MutableList<String> = ArrayList()

    private val adapter = VpAdapter(this, cityList)

    lateinit var citySearch : GetCitySearchUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this, activityResultRegistry))
            .get(MainViewModel::class.java)

        mainViewModel.resultCoord.observe(this, Observer {
            cityList.add(it)
            binding.viewPager.adapter = adapter
            binding.indicator.setViewPager(binding.viewPager)

        })

        fun addCity(city: SearchCityItem){
            adapter.addCity(city)
            //обновление индикатора
            binding.indicator.setViewPager(binding.viewPager)
            //открытие страницы с выбранным городом
            binding.viewPager.currentItem = cityList.size
            Log.d("CityLog", "$cityList")
        }

        //добавление города
        citySearch = GetCitySearchUseCase(activityResultRegistry){
            addCity(it)
    }

        binding.btDelete.setOnClickListener {
            val position = binding.viewPager.currentItem
            adapter.deleteCity(position)
            binding.indicator.setViewPager(binding.viewPager)
        }

        binding.btSearchActivity.setOnClickListener {
            citySearch.startActivity(this)
        }

        //скрытие кнопки удаления
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







