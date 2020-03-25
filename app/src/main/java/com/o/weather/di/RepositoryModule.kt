package com.o.weather.di

import com.o.weather.data.repository.weather.WeatherRepository
import com.o.weather.data.repository.weather.WeatherRepositoryImp
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val RepositoryModule = Kodein.Module("Repository_Module"){
        bind<WeatherRepository>() with singleton { WeatherRepositoryImp(instance(), instance()) }
}


