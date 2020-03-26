package com.o.weather.data.model


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val currentWeatherId = 0


data class WeatherResponse(
    @SerializedName("current")
    val weather: Weather,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
) {


    @Entity(tableName = "current_weather")
    data class Weather(
        @PrimaryKey(autoGenerate = false)
        var id: Int = currentWeatherId,
        @SerializedName("feelslike")
        var feelslike: Double =0.0,
        @SerializedName("precip")
        var precip: Double=0.0,
        @SerializedName("temperature")
        var temperature: Double=0.0,
        @SerializedName("visibility")
        var visibility: Double=0.0,
        @SerializedName("wind_dir")
        var windDir: String="",
        @SerializedName("wind_speed")
        var windSpeed: Double=0.0,
        @SerializedName("weather_icons")
        @Ignore
        var weatherIcons: List<String> = emptyList(),
        var icon: String="",
        @SerializedName("weather_descriptions")
        @Ignore
        var weatherDescriptions: List<String> = emptyList(),
        var description: String="",
        var country: String = "",
        var city: String = ""
    ){
       // constructor(id: Int, feelsLike: Double, precip: Double, temperature: Double, visibility: Double): this(id, feelsLike, precip, temperature, visibility, windDir, windSpeed)
    }


    data class Location(
        @SerializedName("country")
        val country: String,
        @SerializedName("lat")
        val lat: String,
        @SerializedName("localtime")
        val localtime: String,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Int,
        @SerializedName("lon")
        val lon: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("timezone_id")
        val timezoneId: String,
        @SerializedName("utc_offset")
        val utcOffset: String
    )

    data class Request(
        @SerializedName("language")
        val language: String,
        @SerializedName("query")
        val query: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("unit")
        val unit: String
    )
}