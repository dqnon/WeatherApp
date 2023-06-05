package com.example.weathermvvm.Utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class Permission(
    private val context: Context,
    private val activityResultRegistry: ActivityResultRegistry
) {
    private lateinit var pLauncher: ActivityResultLauncher<String>

    fun checkLocationPermission(): Boolean {
        when{
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED -> {
                //Toast.makeText(context, "permission granted", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                return false
            }
        }

    }

    fun registerPermissionListener() {

        pLauncher = activityResultRegistry.register(REGISTER_KEY, ActivityResultContracts.RequestPermission()){
            if(it){

                //Toast.makeText(context, "location run", Toast.LENGTH_SHORT).show()
                Log.d("permissionLog", "location run")
            } else {
                //Toast.makeText(context, "allow access to geolocation for the application to work correctly", Toast.LENGTH_LONG).show()
                Log.d("permissionLog", "allow access to geolocation for the application to work correctly")
            }
        }

    }

    companion object{
        const val REGISTER_KEY = "Location"
    }

}