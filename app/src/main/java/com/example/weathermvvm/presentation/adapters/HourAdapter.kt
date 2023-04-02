package com.example.weathermvvm.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.HoursItemBinding
import com.example.weathermvvm.domain.model.forecast.Hour
import com.squareup.picasso.Picasso

class HourAdapter: ListAdapter<Hour, HourAdapter.HourItemViewHolder>(Comparator()) {

    class HourItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = HoursItemBinding.bind(view)

        fun bind(forecast: Hour) = with(binding){
            tvTemp.text = "${forecast.temp_c} °C"
            tvTime.text = forecast.time.substring(11)
            Picasso.get().load("https:" + forecast.condition.icon).into(imIcon)
            Log.d("MyLog", "АДАПТЕР ПРОГНОЗ ПО ЧАСАМ ${forecast.condition.icon}")

        }
    }

    class Comparator: DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hours_item, parent, false)
        return HourItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourItemViewHolder, position: Int) {
        holder.bind(getItem(position))

    }


}





