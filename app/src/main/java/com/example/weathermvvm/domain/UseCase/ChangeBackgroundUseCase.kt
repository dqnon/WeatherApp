package com.example.weathermvvm.domain.UseCase

import com.example.weathermvvm.R

class ChangeBackgroundUseCase() {

    fun changeBackground(condition: Int): Int {

        var path = when (condition) {
            1000 -> R.drawable.sunny

            1183, 1180, 1186, 1189, 1192, 1195, 1198, 1063 -> R.drawable.rain

            1030, 1135, 1147 -> R.drawable.mist

            1210, 1213, 1216, 1219, 1222, 1225, 1255, 1258 -> R.drawable.snow

            1009 -> R.drawable.overcast

            1006, 1003 -> R.drawable.cloudy

            1273, 1276, 1279, 1282 -> R.drawable.thunder

            else -> R.drawable.weather
        }

        return path
    }
}