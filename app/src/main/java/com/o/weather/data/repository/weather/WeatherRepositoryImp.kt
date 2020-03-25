package com.o.weather.data.repository.weather

import com.o.weather.data.local.WeatherDao
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.data.remote.WeatherApi
import io.reactivex.Single

class WeatherRepositoryImp(val weatherApi: WeatherApi, val weatherDao: WeatherDao): WeatherRepository {


    override fun getCurrentWeather(city: String, unit: String, shouldRefresh: Boolean): Single<Weather> {
        return if(shouldRefresh)
            remoteCurrentWeather(city, unit).onErrorResumeNext {
                localCurrentWeather()
            }
        else
            localCurrentWeather().onErrorResumeNext {
                remoteCurrentWeather(city, unit)
            }
    }

    private fun remoteCurrentWeather(city: String, unit: String): Single<Weather>{
        return weatherApi.getCurrentWeather(city, unit).map {
            it.weather.icon = it.weather.weatherIcons!![0]
            it.weather.description = it.weather.weatherDescriptions!![0]
            return@map it.weather
        }.doOnSuccess { weather ->
            weatherDao.upsert(weather)
        }
    }

    private fun localCurrentWeather(): Single<Weather>{
        return weatherDao.getCurrentWeather()
    }

}