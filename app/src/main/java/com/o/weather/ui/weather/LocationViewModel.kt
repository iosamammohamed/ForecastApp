package com.o.weather.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.o.weather.providers.location.LocationProvider

class LocationViewModel(private val locationProvider: LocationProvider): ViewModel() {

    fun getLocation(): LiveData<Location>{
        return locationProvider.getLocation()
    }

}