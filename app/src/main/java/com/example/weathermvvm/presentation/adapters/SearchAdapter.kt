package com.example.weathermvvm.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.SearchItemBinding
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem

class SearchAdapter(val listener: Listener):
    ListAdapter<SearchCityItem, SearchAdapter.SearchItemViewHolder>(Comparator()) {



    class SearchItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = SearchItemBinding.bind(view)


        fun bind(searchItem: SearchCityItem, listener: Listener) = with(binding){
            tvCity.text = searchItem.name
            tvCountry.text = searchItem.country

            Log.d("SearchLog", "АДАПТЕР ПРОГНОЗ ПО ПОИСКУ ${searchItem.name}")

//            list.forEach {
//                if (it.city == searchItem.name){
//                    imAddCity.setImageResource(R.drawable.ic_launcher_foreground)
//                }
//            }

            imAddCity.setOnClickListener {
                listener.onClick(searchItem)
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<SearchCityItem>() {
        override fun areItemsTheSame(oldItem: SearchCityItem, newItem: SearchCityItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: SearchCityItem, newItem: SearchCityItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)

    }

    interface Listener{
        fun onClick(city: SearchCityItem)
    }

}