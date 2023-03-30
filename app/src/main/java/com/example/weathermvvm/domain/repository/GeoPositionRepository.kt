package com.example.weathermvvm.domain.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.tasks.Task

interface GeoPositionRepository {

    fun getLocation(context: Context): Task<Location>
}