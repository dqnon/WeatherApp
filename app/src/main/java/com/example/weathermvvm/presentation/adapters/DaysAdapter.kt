package com.example.weathermvvm.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.DaysForecastItemBinding
import com.example.weathermvvm.databinding.HoursItemBinding
import com.example.weathermvvm.domain.model.forecast.Day
import com.example.weathermvvm.domain.model.forecast.Forecastday
import com.squareup.picasso.Picasso
import kotlin.time.Duration.Companion.days

class DaysAdapter(private val onClickListener: OnClickListener): ListAdapter<Forecastday, DaysAdapter.DaysItemViewHolder>(Comparator()) {

    var selectedPosition = 0

    class DaysItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = DaysForecastItemBinding.bind(view)

        fun bind(forecast: Forecastday) = with(binding){
            tvTemp.text = "${forecast.day.avgtemp_c}°C"
            tvCondition.text = forecast.day.condition.text
            tvDate.text = forecast.date
            Picasso.get().load("https:" + forecast.day.condition.icon).into(imCondition)
            Log.d("MyLog", "АДАПТЕР ПРОГНОЗ ПО ДНЯМ${forecast.day.avgtemp_c}")
        }
    }

    class Comparator: DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_forecast_item, parent, false)
        return DaysItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysItemViewHolder, position: Int) {
        holder.bind(getItem(position))

        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#943C3B3B"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#6D5D5B5B"))

        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)

            selectedPosition=position
            notifyDataSetChanged()

        }


    }

    class OnClickListener(val clickListener: (item: Forecastday) -> Unit) {
        fun onClick(item: Forecastday) {
            clickListener(item)

        }
    }


}