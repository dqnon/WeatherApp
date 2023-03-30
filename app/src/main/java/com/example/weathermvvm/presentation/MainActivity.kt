package com.example.weathermvvm.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.domain.model.forecast.Forecastday
import com.example.weathermvvm.presentation.adapters.DaysAdapter
import com.example.weathermvvm.presentation.adapters.HourAdapter
import com.example.weathermvvm.presentation.viewmodel.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterHour: HourAdapter
    lateinit var adapterDays: DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSearchActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext, activityResultRegistry))
            .get(MainViewModel::class.java)

        mainViewModel.conditionBackGround.observe(this, Observer { imageId ->
            binding.backImage.setImageResource(imageId)
        })

        //forecast
        mainViewModel.resultForecast.observe(this, Observer { it ->

            binding.tvTemperatura.text = "${it.current.temp_c}°C"
            binding.tvLocation.text = it.location.name
            binding.tvCondition.text = it.current.condition.text
            binding.tvLastUpdated.text = it.current.last_updated
            mainViewModel.changeBackground(it.current.condition.text)

            //прогноз по часам
            adapterHour = HourAdapter()
            binding.rcViewHour.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rcViewHour.adapter = adapterHour

            adapterHour.submitList(it.forecast.forecastday[0].hour)
            //Log.d("MyLog", "ТЕМПЕРАТУРА ${it.forecast.forecastday[0].hour}")

            //прогноз по дням
            adapterDays = DaysAdapter(DaysAdapter.OnClickListener {
                adapterHour.submitList(it.hour) }
            )

            binding.rcViewDays.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcViewDays.adapter = adapterDays

            adapterDays.submitList(it.forecast.forecastday)

        })

        binding.getWeather.setOnClickListener {
            CoroutineScope(Dispatchers.IO + mainViewModel.coroutineExceptionHandler).launch {
                mainViewModel.getLocationData()

            }
        }
    }


}




