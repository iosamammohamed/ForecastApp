package com.o.weather.di

import com.o.weather.ui.weather.LocationViewModelFactory
import com.o.weather.ui.weather.current.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val ViewModelFactoryModule = Kodein.Module("Viewmodel_Factory"){
    bind<WeatherViewModelFactory>() with singleton { WeatherViewModelFactory(instance()) }
    bind<LocationViewModelFactory>() with singleton { LocationViewModelFactory(instance()) }

}