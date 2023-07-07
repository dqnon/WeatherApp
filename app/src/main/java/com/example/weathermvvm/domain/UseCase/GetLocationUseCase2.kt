package com.example.weathermvvm.domain.UseCase

import android.location.Location
import com.example.weathermvvm.domain.repository.LocationRepository

class GetLocationUseCase2(private val locationRepository: LocationRepository) {

    fun getLocation(onLocationReceived: (Location?) -> Unit) =
        locationRepository.getLocation(onLocationReceived)
}