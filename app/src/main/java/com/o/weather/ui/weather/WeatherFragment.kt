package com.o.weather.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.o.weather.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import com.o.weather.data.model.WeatherResponse.Weather
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment(R.layout.weather_fragment), KodeinAware {

    override val kodein by closestKodein()

    private val weatherViewModelFactory: WeatherViewModelFactory by instance()
    private lateinit var viewModel: WeatherViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, weatherViewModelFactory).get(WeatherViewModel::class.java)
        viewModel.getCurrentWeather("Egypt", "m", shouldRefresh = false)
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getCurrentWeather("Egypt", "m", shouldRefresh = true)
        }
        bindUI()
    }

    private fun bindUI(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner,
            Observer<Weather> {weather ->
                println("hhhhhhhhhhhhhh ${weather}")
                updateWeather(weather)
                data_layout.visibility = VISIBLE
            })

        viewModel.isLoading.observe(viewLifecycleOwner,
            Observer<Boolean>{isLoading ->
                if(isLoading) {
                    loading_layout.visibility = VISIBLE
                    data_layout.visibility = GONE
                } else{
                    swipe_refresh_layout.isRefreshing = false
                    loading_layout.visibility = GONE
                }
            })
    }


    private fun updateWeather(weather: Weather) {
        (activity as AppCompatActivity).supportActionBar?.title = "Egypt"
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
        weather_temperature.text = weather.temperature.toString()
        weather_feels_like_temperature.text = "Feels like ${weather.feelslike}"
        weather_precipitation.text = "Preciptiation: ${weather.precip}"
        weather_wind.text = "Wind: ${weather.windDir}, ${weather.windSpeed}"
        weather_visibility.text = "Visibility: ${weather.visibility}"
        weather_description.text = weather.description
        Glide.with(this@WeatherFragment)
            .load(weather.icon)
            .into(weather_condition_icon)
    }

}
