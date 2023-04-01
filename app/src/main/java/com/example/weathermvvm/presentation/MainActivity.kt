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
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    val cityList = mutableListOf<String>("", "Moscow", "Samara", "Bryansk", "Novosibirsk")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext))
            .get(MainViewModel::class.java)


        mainViewModel.getLocationData()

        mainViewModel.resultCoord.observe(this, Observer {
            cityList[0] = it

            binding.viewPager.adapter = VpAdapter(this, cityList)
            Log.d("CoordLog", "${cityList}")
        })


        binding.btSearchActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        }
    }







