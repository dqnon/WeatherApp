package com.example.weathermvvm.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.FragmentMainBinding
import com.example.weathermvvm.presentation.adapters.DaysAdapter
import com.example.weathermvvm.presentation.adapters.HourAdapter
import com.example.weathermvvm.presentation.viewmodel.MainViewModel
import com.example.weathermvvm.presentation.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ARG_OBJECT = "object"
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel
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


        mainViewModel = ViewModelProvider(this, MainViewModelFactory(requireContext()))
            .get(MainViewModel::class.java)

        mainViewModel.conditionBackGround.observe(requireActivity(), Observer { imageId ->
            binding.backImage.setImageResource(imageId)
        })

        //forecast
        mainViewModel.resultForecast.observe(requireActivity(), Observer { it ->

            binding.tvTemperatura.text = "${it.current.temp_c}°C"
            binding.tvLocation.text = it.location.name
            binding.tvCondition.text = it.current.condition.text
            binding.tvLastUpdated.text = it.current.last_updated
            mainViewModel.changeBackground(it.current.condition.text)

            //прогноз по часам
            adapterHour = HourAdapter()
            binding.rcViewHour.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcViewHour.adapter = adapterHour

            adapterHour.submitList(it.forecast.forecastday[0].hour)
            //Log.d("MyLog", "ТЕМПЕРАТУРА ${it.forecast.forecastday[0].hour}")

            //прогноз по дням
            adapterDays = DaysAdapter(DaysAdapter.OnClickListener {
                adapterHour.submitList(it.hour) }
            )

            binding.rcViewDays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rcViewDays.adapter = adapterDays

            adapterDays.submitList(it.forecast.forecastday)

        })

        binding.getWeather.setOnClickListener {
            CoroutineScope(Dispatchers.IO + mainViewModel.coroutineExceptionHandler).launch {
                mainViewModel.getLocationData()

            }
        }

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            var tempVar = getString(ARG_OBJECT).toString()
            CoroutineScope(Dispatchers.IO + mainViewModel.coroutineExceptionHandler).launch {
                mainViewModel.getForecastData(tempVar)
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()


    }
}