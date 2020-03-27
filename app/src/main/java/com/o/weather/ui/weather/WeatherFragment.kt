package com.o.weather.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.o.weather.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.providers.preference.PreferencesProvider
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment(R.layout.weather_fragment), KodeinAware {

    override val kodein by closestKodein()

    private val preferences: PreferencesProvider by instance()
    private val weatherViewModelFactory: WeatherViewModelFactory by instance()
    private val locationViewModelFactory: LocationViewModelFactory by instance()
    private lateinit var viewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel
    private val locationRequestCode = 1000


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        viewModel = ViewModelProvider(this, weatherViewModelFactory).get(WeatherViewModel::class.java)
        locationViewModel = ViewModelProvider(this, locationViewModelFactory).get(LocationViewModel::class.java)
        bindUI()
        getWeather()
        swipe_refresh_layout.setOnRefreshListener {
            getWeather()
        }
    }


    private fun getWeather(){
        if(preferences.isUsingDeviceLocation()){
            getLocationAndCheckPermission()
        }else{
            viewModel.getCurrentWeather(preferences.getCustomLocation())
        }
    }

    private fun bindUI(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner,
            Observer<Weather> {weather ->
                updateWeather(weather)
                data_layout.visibility = VISIBLE
            })

        viewModel.isLoading.observe(viewLifecycleOwner,
            Observer<Boolean>{isLoading ->
                if(isLoading) {
                    loading_layout.visibility = VISIBLE
                    data_layout.visibility = GONE
                    empty_layout.visibility = GONE
                } else{
                    swipe_refresh_layout.isRefreshing = false
                    loading_layout.visibility = GONE
                }
            })
        viewModel.isEmpty.observe(viewLifecycleOwner,
            Observer<Boolean>{isEmpty ->
                if(isEmpty){
                    empty_layout.visibility = VISIBLE
                }
            })
    }
    private fun updateWeather(weather: Weather) {
        (activity as AppCompatActivity).supportActionBar?.title = "${weather.city}, ${weather.country}"
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
        weather_temperature.text = "${weather.temperature} C"
        weather_feels_like_temperature.text = "Feels like ${weather.feelslike} C"
        weather_precipitation.text = "Preciptiation: ${weather.precip} MM"
        weather_wind.text = "Wind: ${weather.windDir}, ${weather.windSpeed} Km/h"
        weather_visibility.text = "Visibility: ${weather.visibility}"
        weather_description.text = weather.description
        Glide.with(this@WeatherFragment)
            .load(weather.icon)
            .into(weather_condition_icon)
    }


    private fun getLocationAndCheckPermission(){
        if (checkSelfPermission(context!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(context!!.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                locationRequestCode)
        } else {
            getLocation()
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1000 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()

                } else {
                    Toast.makeText(context!!.applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getLocation(){
               locationViewModel.getLocation().observe(viewLifecycleOwner,
                   Observer<Location> {
                       location ->
                       viewModel.getCurrentWeather("${location.latitude},${location.longitude}")
                   })
    }

}
