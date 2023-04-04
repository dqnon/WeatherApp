package com.example.weathermvvm.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.ARG_CITY
import com.example.weathermvvm.presentation.MainFragment

class VpAdapter(fa: FragmentActivity,  private val list: MutableList<String>): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_CITY, list[position])
        }
        return fragment
    }

    fun addCity(cityItem: SearchCityItem) {
        list.add(cityItem.name)
        notifyDataSetChanged()
    }

    fun deleteCity(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }



}