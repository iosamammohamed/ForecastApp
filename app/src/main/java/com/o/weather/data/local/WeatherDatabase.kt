package com.o.weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.o.weather.data.model.WeatherResponse.Weather

@Database(entities = arrayOf(Weather::class), version = 1)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun currentWeatherDao(): WeatherDao


    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance
            ?: synchronized(LOCK){
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it}
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            WeatherDatabase::class.java, "weather.db")
            .build()
    }


}



