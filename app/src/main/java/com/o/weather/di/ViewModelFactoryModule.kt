package com.o.weather.di

import com.o.weather.data.repository.weather.WeatherRepository
import com.o.weather.data.repository.weather.WeatherRepositoryImp
import com.o.weather.ui.weather.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val ViewModelFactoryModule = Kodein.Module("Viewmodel_Factory"){
    bind<WeatherViewModelFactory>() with singleton { WeatherViewModelFactory(instance()) }
}