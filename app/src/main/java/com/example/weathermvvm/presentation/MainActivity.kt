package com.example.weathermvvm.presentation

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.databinding.ActivityMainBinding
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.adapters.VpAdapter
import com.example.weathermvvm.presentation.viewmodel.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    //убрать
    val cityList: MutableList<String> = ArrayList()
    private val adapter = VpAdapter(this, cityList)
    private var searchLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //возможно перенести в отдельный класс
        searchLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val text = result.data?.getSerializableExtra("city") as SearchCityItem
                Toast.makeText(this, "${text.name}", Toast.LENGTH_SHORT).show()
                adapter.addCity(text)
                //обновление индикатора
                binding.indicator.setViewPager(binding.viewPager)
            }
        }

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext))
            .get(MainViewModel::class.java)

        mainViewModel.resultCoord.observe(this, Observer {
            cityList.add(it)
            binding.viewPager.adapter = adapter
            Log.d("CoordLog", "${cityList}")

            binding.indicator.setViewPager(binding.viewPager)


        })

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
            searchLauncher?.launch(Intent(this, SearchActivity::class.java))
        }





    }
}







