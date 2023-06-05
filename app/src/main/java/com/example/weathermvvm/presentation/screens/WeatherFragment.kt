package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.data.toRoomModel
import com.example.weathermvvm.databinding.FragmentMainBinding
import com.example.weathermvvm.presentation.adapters.DaysAdapter
import com.example.weathermvvm.presentation.adapters.HourAdapter
import com.example.weathermvvm.presentation.viewmodel.weather.WeatherViewModel
import com.example.weathermvvm.presentation.viewmodel.weather.WeatherViewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ARG_CITY = "city"
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var adapterHour: HourAdapter
    lateinit var adapterDays: DaysAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(requireContext()))
            .get(WeatherViewModel::class.java)

        weatherViewModel.conditionBackGround.observe(viewLifecycleOwner, Observer { imageId ->
            binding.backImage.setImageResource(imageId)
        })

        //forecast
        weatherViewModel.resultForecast.observe(viewLifecycleOwner, Observer { it ->

            binding.tvTemperatura.text = "${it.temp}°C"
            binding.tvLocation.text = it.locationName
            binding.tvCondition.text = it.conditionText
            binding.tvLastUpdated.text = it.lastUpdated

            weatherViewModel.changeBackground(it.conditionCode)

            //прогноз по часам
            adapterHour = HourAdapter()
            binding.rcViewHour.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcViewHour.adapter = adapterHour

            //пролистывание до нужного элемента по времени
            (binding.rcViewHour.layoutManager as LinearLayoutManager).scrollToPosition(
                it.lastUpdated.substring(11, 13).toInt())

            adapterHour.submitList(it.forecastday[0].hour)
            Log.d("MyLog", "ТЕМПЕРАТУРА ${it.forecastday[0].hour}")

            //прогноз по дням
            adapterDays = DaysAdapter(DaysAdapter.OnClickListener {
                adapterHour.submitList(it.hour) }
            )

            binding.rcViewDays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rcViewDays.adapter = adapterDays

            adapterDays.submitList(it.forecastday)
        })



        //получение названия города для создания новых фрагментов
        arguments?.takeIf { it.containsKey(ARG_CITY) }?.apply {
            var cityArg = getString(ARG_CITY).toString()

            val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                CoroutineScope(Dispatchers.IO).launch {
                    //weatherViewModel.getFragmentByCity(cityArg)
                    Log.d("MyLog", "ЗДЕСЬ ДОЛЖЕН БЫТЬ РУМ")
                }
            }

            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                //weatherViewModel.getForecastData(cityArg)
                weatherViewModel.getForecastData(cityArg)
                //где то здесь сделать реализацию рума
            }

            //обновление данных
            binding.swipeRefresh.setOnRefreshListener {
                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                    //weatherViewModel.getForecastData(cityArg)
                    weatherViewModel.getForecastData(cityArg)
                }
                //возможно убрать?
                binding.swipeRefresh.isRefreshing = false

            }
        }
    }
}