package com.o.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.o.weather.providers.location.LocationProvider

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory(private val locationProvider: LocationProvider): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  LocationViewModel(locationProvider) as T
    }
}