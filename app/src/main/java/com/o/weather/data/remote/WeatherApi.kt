package com.o.weather.data.remote

import com.o.weather.data.model.WeatherResponse
import com.o.weather.util.Constants
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

// http://api.weatherstack.com/current?access_key=1ac9c49efaa04c2356d314d3643bceb5&query=NewYork

interface WeatherApi {

    @GET(Constants.CURRENT)
    fun getCurrentWeather(
        @Query(Constants.QUERY) city: String
    ): Single<WeatherResponse>


}