package com.example.weathermvvm.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.MainViewModelFactory

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



        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext, activityResultRegistry))
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
        }

        //добавление города
        citySearch = GetCitySearchUseCase(activityResultRegistry){
            addCity(it)
    }


        binding.btDelete.setOnClickListener {
            val position = binding.viewPager.currentItem
            if (position != 0){
                adapter.deleteCity(position)
                binding.indicator.setViewPager(binding.viewPager)
            } else {
                Toast.makeText(this, "Вы не можете удалить этот элемент", Toast.LENGTH_SHORT).show()
            }

        }



        binding.btSearchActivity.setOnClickListener {
            citySearch.startActivity(this)
        }





    }


}







