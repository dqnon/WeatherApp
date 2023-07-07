package com.example.weathermvvm.presentation.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.presentation.screens.ARG_CITY
import com.example.weathermvvm.presentation.screens.WeatherFragment

class VpAdapter(fa: FragmentActivity ): FragmentStateAdapter(fa) {
    var list = mutableListOf<WeatherCityItem>()

    override fun getItemCount(): Int {
        Log.d("adapLog", "$list")
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = WeatherFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_CITY, list[position].city)
        }
        return fragment
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCity(cityItem: WeatherCityItem) {
        list.add(cityItem)
        notifyItemInserted(list.size+1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteCity(position: Int){
        Log.d("PosLog", "$list")
        Log.d("PosLog", "$position")
        list.removeAt(position)
        notifyItemRemoved(position)
    }

}