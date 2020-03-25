package com.o.weather.di

import android.content.Context
import com.o.weather.data.local.WeatherDao
import com.o.weather.data.local.WeatherDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


val DatabaseModule = Kodein.Module("Database_Module"){
    bind<WeatherDatabase>() with singleton { getDatabase(instance()) }
    bind<WeatherDao>() with singleton { getCurrentWeatherDao(instance()) }
}

fun getDatabase(context: Context): WeatherDatabase{
    return WeatherDatabase.invoke(context)
}

    fun getCurrentWeatherDao(database: WeatherDatabase): WeatherDao{
        return database.currentWeatherDao()
    }

