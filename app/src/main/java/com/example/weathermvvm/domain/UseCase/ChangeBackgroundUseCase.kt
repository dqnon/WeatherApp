package com.example.weathermvvm.domain.UseCase

import com.example.weathermvvm.R

class ChangeBackgroundUseCase() {

    fun changeBackground(condition: String): Int {

        var path = when (condition) {
            "Sunny" -> R.drawable.sunny
            "Light Rain" -> R.drawable.light_rain
            "Mist", "Fog" -> R.drawable.mist
            "Patchy snow possible",
            "Blowing snow",
            "Patchy light snow",
            "Light snow",
            "Heavy snow"
            -> R.drawable.snow
            "Overcast" -> R.drawable.overcast
            else -> R.drawable.weather
        }

        return path
    }
}