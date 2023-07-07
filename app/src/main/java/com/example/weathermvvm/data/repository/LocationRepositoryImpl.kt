package com.example.weathermvvm.data.repository

import android.location.Location
import android.util.Log
import com.example.weathermvvm.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient

class LocationRepositoryImpl(private val fusedLocationClient: FusedLocationProviderClient) :
    LocationRepository {

    override fun getLocation(onLocationReceived: (Location?) -> Unit) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                onLocationReceived(location)
            }
        } catch (e: SecurityException) {
            Log.e("locationTAG", "Error occurred while getting the last known location: ${e.message}")
            onLocationReceived(null)
        }
    }
}