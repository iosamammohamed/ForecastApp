package com.o.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.data.model.currentWeatherId
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weather: Weather): Long

    @Query("SELECT * FROM current_weather WHERE id = $currentWeatherId")
    fun getCurrentWeather(): Single<Weather>


}