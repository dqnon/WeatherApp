package com.example.weathermvvm.presentation.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weathermvvm.db.WeatherItem
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.screens.ARG_CITY
import com.example.weathermvvm.presentation.screens.MainFragment

class VpAdapter(fa: FragmentActivity ): FragmentStateAdapter(fa) {
    var list = mutableListOf<WeatherItem>(WeatherItem(null, ""))


    fun setCityList(listCity:  MutableList<WeatherItem>){
        list.clear()
        list += listCity
    }


    override fun getItemCount(): Int {
        Log.d("adapLog", "$list")
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_CITY, list[position].city)
        }
        return fragment
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCity(cityItem: SearchCityItem) {
        list.add(WeatherItem(null, cityItem.name))
        notifyDataSetChanged()
    }

    fun deleteCity(position: Int){
        Log.d("PosLog", "$list")
        Log.d("PosLog", "$position")
        list.removeAt(position)
        notifyDataSetChanged()
    }

}