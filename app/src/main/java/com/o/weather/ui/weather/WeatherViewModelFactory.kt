package com.o.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.o.weather.data.repository.weather.WeatherRepository

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(val weatherRepository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}