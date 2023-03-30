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
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem

class SearchAdapter: ListAdapter<SearchCityItem, SearchAdapter.SearchItemViewHolder>(Comparator()) {

    class SearchItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = SearchItemBinding.bind(view)

        fun bind(searchItem: SearchCityItem) = with(binding){
            tvCity.text = searchItem.name
            tvCountry.text = searchItem.country
            Log.d("SearchLog", "АДАПТЕР ПРОГНОЗ ПО ПОИСКУ ${searchItem.name}")
        }
    }

    class Comparator: DiffUtil.ItemCallback<SearchCityItem>() {
        override fun areItemsTheSame(oldItem: SearchCityItem, newItem: SearchCityItem): Boolean {
            return oldItem == newItem
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
        holder.bind(getItem(position))

    }


}