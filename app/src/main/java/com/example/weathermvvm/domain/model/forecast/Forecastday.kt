package com.example.weathermvvm.domain.model.forecast

import androidx.room.Embedded

data class Forecastday(
    @Embedded
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    @Embedded
    val day: Day,
    val hour: List<Hour>
)