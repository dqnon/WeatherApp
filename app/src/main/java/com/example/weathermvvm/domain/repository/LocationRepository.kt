package com.example.weathermvvm.domain.repository

import android.location.Location

interface LocationRepository {
    fun getLocation(onLocationReceived: (Location?) -> Unit)
}