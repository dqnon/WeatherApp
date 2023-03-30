package com.example.weathermvvm.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.weathermvvm.domain.repository.GeoPositionRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class GeoPositionRepositoryImpl: GeoPositionRepository {
    private lateinit var fLocationClient: FusedLocationProviderClient
    @SuppressLint("MissingPermission")
    override fun getLocation(context: Context): Task<Location> {
        fLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val task = fLocationClient.lastLocation
//убрать
        if (task != null) {
            return task
        }
        return task
    }
}