package com.example.weathermvvm.domain.UseCase

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.weathermvvm.domain.repository.GeoPositionRepository
import com.google.android.gms.tasks.Task


class GetLocationUseCase(private val geoPositionRepository: GeoPositionRepository,
                         private val context: Context) {


    @SuppressLint("SuspiciousIndentation")
    fun getActualLocation(): Task<Location> {
        var locationItem = geoPositionRepository.getLocation(context)
        return locationItem
    }

}
