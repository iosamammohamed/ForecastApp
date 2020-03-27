package com.o.weather.data.repository.weather

import com.o.weather.data.local.WeatherDao
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.data.remote.WeatherApi
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat

class WeatherRepositoryImp(private val weatherApi: WeatherApi,private val weatherDao: WeatherDao): WeatherRepository {


    override fun getCurrentWeather(city: String): Single<Weather> {
        return remoteCurrentWeather(city).onErrorResumeNext {
            localCurrentWeather()
        }
    }

    private fun remoteCurrentWeather(city: String): Single<Weather>{
        return weatherApi.getCurrentWeather(city).map {
            it.weather.icon = it.weather.weatherIcons[0]
            it.weather.description = it.weather.weatherDescriptions[0]
            it.weather.country = it.location.country
            it.weather.city = it.location.name
            return@map it.weather
        }.doOnSuccess { weather ->
            weatherDao.upsert(weather)
        }
    }

    private fun localCurrentWeather(): Single<Weather>{
        return weatherDao.getCurrentWeather()
    }

}