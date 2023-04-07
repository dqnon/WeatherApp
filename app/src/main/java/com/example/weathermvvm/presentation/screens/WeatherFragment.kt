package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.databinding.FragmentMainBinding
import com.example.weathermvvm.presentation.adapters.DaysAdapter
import com.example.weathermvvm.presentation.adapters.HourAdapter
import com.example.weathermvvm.presentation.viewmodel.weather.WeatherViewModel
import com.example.weathermvvm.presentation.viewmodel.weather.WeatherViewModelFactory
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

            binding.tvTemperatura.text = "${it.current.temp_c}°C"
            binding.tvLocation.text = it.location.name
            binding.tvCondition.text = it.current.condition.text
            binding.tvLastUpdated.text = it.current.last_updated

            weatherViewModel.changeBackground(it.current.condition.text)

            //прогноз по часам
            adapterHour = HourAdapter()
            binding.rcViewHour.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcViewHour.adapter = adapterHour

            //пролистывание до нужного элемента по времени
            (binding.rcViewHour.layoutManager as LinearLayoutManager).scrollToPosition(
                it.current.last_updated.substring(11, 13).toInt())

            adapterHour.submitList(it.forecast.forecastday[0].hour)
            Log.d("MyLog", "ТЕМПЕРАТУРА ${it.forecast.forecastday[0].hour}")

            //прогноз по дням
            adapterDays = DaysAdapter(DaysAdapter.OnClickListener {
                adapterHour.submitList(it.hour) }
            )

            binding.rcViewDays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rcViewDays.adapter = adapterDays

            adapterDays.submitList(it.forecast.forecastday)

        })

        //получение названия города для создания новых фрагментов
        arguments?.takeIf { it.containsKey(ARG_CITY) }?.apply {
            var cityArg = getString(ARG_CITY).toString()
            CoroutineScope(Dispatchers.IO + weatherViewModel.coroutineExceptionHandler).launch {
                weatherViewModel.getForecastData(cityArg)
            }

            //обновление данных
            binding.swipeRefresh.setOnRefreshListener {
                CoroutineScope(Dispatchers.IO + weatherViewModel.coroutineExceptionHandler).launch {
                    weatherViewModel.getForecastData(cityArg)
                }
                //возможно убрать?
                binding.swipeRefresh.isRefreshing = false

            }
        }
    }
}