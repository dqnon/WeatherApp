package com.example.weathermvvm.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermvvm.LOCATION_PERMISSION_REQUEST_CODE
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.UseCase.GetCitySearchUseCase
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.main.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    //lateinit var mainViewModel: MainViewModel
    private val adapter = VpAdapter(this)
    lateinit var citySearch : GetCitySearchUseCase//УБРАТЬ


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Проверка разрешения на геолокацию
        checkLocationPermission()

        mainViewModel.resultCoord.observe(this, Observer { location ->
            val geoLocationItem = WeatherCityItem(id = 1, city = "${location[0]},${location[1]}")
            mainViewModel.addCityRoom(geoLocationItem)
        })

        mainViewModel.allItemsCity.observe(this, Observer { list ->
            adapter.list = list
            binding.viewPager.adapter = adapter
            binding.indicator.setViewPager(binding.viewPager)
        })

        //удаление города
        binding.btDelete.setOnClickListener {
            val position = binding.viewPager.currentItem
            mainViewModel.delCityRoom(adapter.list[position])
            Log.d("roomLog", "${adapter.list[position]}")
            adapter.deleteCity(position)
            binding.indicator.setViewPager(binding.viewPager)
        }

        //добавление города
        citySearch = GetCitySearchUseCase(activityResultRegistry) {
            val itemWeather = WeatherCityItem(null, it.name)
            mainViewModel.addCityRoom(itemWeather)
            adapter.addCity(itemWeather)
            //binding.viewPager.setCurrentItem(adapter.list.lastIndex, true)
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

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение уже получено, выполняйте соответствующие действия здесь
            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.getAllItems()
                mainViewModel.getLocationData()
            }
        } else {
            // Разрешение не получено, запросите его у пользователя
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.getAllItems()
                    mainViewModel.getLocationData()
                }
            } else {
                Toast.makeText(this, "Разрешение не получено", Toast.LENGTH_SHORT).show()
                Log.e("PermLog", "Разрешение не получено")
            }
        }
    }

}







