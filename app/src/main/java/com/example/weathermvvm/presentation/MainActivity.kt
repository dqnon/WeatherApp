package com.example.weathermvvm.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.domain.model.forecast.Forecastday
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
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
    //убрать
    val cityList: MutableList<String> = ArrayList()
    private var searchLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        searchLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//                result: ActivityResult ->
//            if (result.resultCode == RESULT_OK){
//                Log.d("IntentLog", "Main Activity ${result.data?.getStringExtra("city")}----------")
//            }
//        }

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("city")
            Log.d("IntentLog", "Mainactivity $value")
            //The key argument here must match that used in the other activity
        }

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext))
            .get(MainViewModel::class.java)

        mainViewModel.resultCoord.observe(this, Observer {
            cityList.add(it)
            binding.viewPager.adapter = VpAdapter(this, cityList)
            Log.d("CoordLog", "${cityList}")

            binding.indicator.setViewPager(binding.viewPager)


        })


        binding.btSearchActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }





    }
}







