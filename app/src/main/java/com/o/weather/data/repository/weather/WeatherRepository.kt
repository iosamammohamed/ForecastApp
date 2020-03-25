package com.o.weather.data.repository.weather

import com.o.weather.data.model.WeatherResponse.Weather
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface WeatherRepository {
       fun getCurrentWeather(city: String, unit: String, shouldRefresh: Boolean): Single<Weather>
}