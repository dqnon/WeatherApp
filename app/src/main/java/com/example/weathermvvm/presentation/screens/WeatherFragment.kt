package com.example.weathermvvm.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.databinding.FragmentMainBinding
import com.example.weathermvvm.presentation.MyDialogFragment
import com.example.weathermvvm.presentation.ProgressState
import com.example.weathermvvm.presentation.adapters.DaysAdapter
import com.example.weathermvvm.presentation.adapters.HourAdapter
import com.example.weathermvvm.presentation.viewmodel.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

const val ARG_CITY = "city"
@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    //lateinit var weatherViewModel: WeatherViewModel
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



//        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(requireContext()))
//            .get(WeatherViewModel::class.java)

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


        weatherViewModel.state.observe(viewLifecycleOwner, Observer {state ->
            when(state){
                ProgressState.Loading -> {
                    binding.tvTemperatura.visibility = View.INVISIBLE
                    binding.tvCondition.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                ProgressState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvTemperatura.visibility = View.VISIBLE
                    binding.tvCondition.visibility = View.VISIBLE
                }
                ProgressState.Error -> {
                    val myDialogFragment = MyDialogFragment()
                    val manager = activity?.supportFragmentManager
                    val transaction: FragmentTransaction = manager!!.beginTransaction()
                    myDialogFragment.show(transaction, "dialog")
                }
            }

        })


        //получение названия города для создания новых фрагментов
        arguments?.takeIf { it.containsKey(ARG_CITY) }?.apply {
            var cityArg = getString(ARG_CITY).toString()

            weatherViewModel.getForecastData(cityArg)

            //обновление данных
            binding.swipeRefresh.setOnRefreshListener {
                weatherViewModel.getForecastData(cityArg)

                //возможно убрать?
                binding.swipeRefresh.isRefreshing = false

            }
        }
    }


}