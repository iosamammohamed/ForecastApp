package com.o.weather.data.repository.weather

import com.o.weather.data.local.WeatherDao
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.data.remote.WeatherApi
import io.reactivex.Single

class WeatherRepositoryImp(private val weatherApi: WeatherApi,private val weatherDao: WeatherDao): WeatherRepository {


    override fun getCurrentWeather(city: String, shouldRefresh: Boolean): Single<Weather> {
        return if(shouldRefresh)
            remoteCurrentWeather(city).onErrorResumeNext {
                localCurrentWeather()
            }
        else
            localCurrentWeather().onErrorResumeNext {
                remoteCurrentWeather(city)
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